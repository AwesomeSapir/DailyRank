package com.sapreme.dailyrank.data.repository.Impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import com.sapreme.dailyrank.data.repository.GameResultRepository
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Singleton

@Singleton
class FirebaseGameResultRepository : GameResultRepository {

    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun parse(raw: String, date: LocalDate): Result<GameResult> {
        Timber.d("parse called with:\n - raw: $raw\n - date: $date")

        val cleaned = when {
            raw.startsWith("Archive") -> {
                Timber.d("Detected archive header, stripping it")
                raw.substringAfter("\n")
            }

            else -> raw
        }

        val parser = GameResultParserFactory.from(cleaned) ?: return Result.failure(
            IllegalArgumentException("Unrecognized format")
        )
        Timber.i("Found parser: ${parser::class.simpleName}")

        val result = parser.parse(cleaned, date)
            ?: return Result.failure(IllegalArgumentException("Failed to parse game result"))
        Timber.i("Parsing succeeded: $result")

        return Result.success(result)
    }

    override suspend fun submit(gameResult: GameResult) {
        val userId = auth.currentUser?.uid ?: run {
            Timber.tag("submit").e("No authenticated user")
            return
        }

        val docId = "${gameResult.type.name.lowercase()}_${gameResult.puzzleId}"
        val data = mutableMapOf(
            "puzzleId" to gameResult.puzzleId,
            "date" to gameResult.date,
            "succeeded" to gameResult.succeeded,
            "type" to gameResult.type,
        )
        when (gameResult) {
            is GameResult.WordleResult -> {
                data["attempts"] = gameResult.attempts
            }

            is GameResult.ConnectionsResult -> {
                data["attempts"] = gameResult.attempts
                data["groupings"] = gameResult.groupings
                data["mistakes"] = gameResult.mistakes
            }

            is GameResult.StrandsResult -> {
                data["title"] = gameResult.title
                data["hints"] = gameResult.hints
                data["doubleHints"] = gameResult.doubleHints
                data["words"] = gameResult.words
            }

            is GameResult.MiniResult -> {
                data["duration"] = gameResult.duration.toString() // Store duration appropriately
            }
        }

        firestore
            .collection("players")
            .document(userId)
            .collection("results")
            .document(docId)
            .set(data).addOnSuccessListener { Timber.i("Result submitted successfully") }
            .addOnFailureListener { e -> Timber.e(e, "Failed to submit result") }
    }
}