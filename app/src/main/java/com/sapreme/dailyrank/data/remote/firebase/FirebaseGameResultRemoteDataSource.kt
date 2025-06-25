package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import com.sapreme.dailyrank.ui.util.toFirestoreTimestamp
import com.sapreme.dailyrank.ui.util.toLocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.ZoneOffset
import kotlin.time.Duration

class FirebaseGameResultRemoteDataSource (
    private val firestore: FirebaseFirestore
): GameResultRemoteDataSource{

    override suspend fun getUserGameResultsBy(
        userId: String,
        filter: GameResultFilter
    ): List<GameResult> = withContext(Dispatchers.IO) {
        var query: Query = firestore
            .collection("players")
            .document(userId)
            .collection("results")

        filter.type?.let { query = query.whereEqualTo("type", it.name) }
        filter.startDate?.let {
            query = query.whereGreaterThanOrEqualTo("puzzleDate", it.toFirestoreTimestamp())
        }
        filter.endDate?.let {
            query = query.whereLessThan("puzzleDate", it.plusDays(1).toFirestoreTimestamp())
        }

        query = query.orderBy("puzzleDate", Query.Direction.DESCENDING)

        val snapshot = query.get().await()

        return@withContext snapshot.documents.mapNotNull { parseGameResult(it) }
    }

    override suspend fun publishUserGameResult(userId: String, gameResult: GameResult) {
        val docId = "${gameResult.type.name.lowercase()}_${gameResult.puzzleId}"
        val data = mutableMapOf(
            "puzzleId" to gameResult.puzzleId,
            "puzzleDate" to gameResult.puzzleDate.toFirestoreTimestamp(),
            "submitDate" to gameResult.submitDate.toFirestoreTimestamp(),
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

    private fun parseGameResult(doc: DocumentSnapshot): GameResult? {
        val typeStr = doc.getString("type") ?: return null
        val puzzleId = doc.getLong("puzzleId")?.toInt() ?: return null
        val succeeded = doc.getBoolean("succeeded") ?: false
        val timestamp = doc.getTimestamp("submitDate") ?: return null
        val submitDate = timestamp.toLocalDate()
        val puzzleTimestamp = doc.getTimestamp("puzzleDate") ?: return null
        val puzzleDate = puzzleTimestamp.toLocalDate()

        return when (typeStr.uppercase()) {
            "WORDLE" -> GameResult.WordleResult(
                puzzleId = puzzleId,
                submitDate = submitDate,
                succeeded = succeeded,
                attempts = doc.getLong("attempts")?.toInt() ?: 6
            )

            "CONNECTIONS" -> GameResult.ConnectionsResult(
                puzzleId = puzzleId,
                submitDate = submitDate,
                succeeded = succeeded,
                attempts = doc.getLong("attempts")?.toInt() ?: 0,
                mistakes = doc.getLong("mistakes")?.toInt() ?: 0,
                groupings = doc.getLong("groupings")?.toInt() ?: 0
            )

            "STRANDS" -> GameResult.StrandsResult(
                puzzleId = puzzleId,
                submitDate = submitDate,
                succeeded = succeeded,
                title = doc.getString("title") ?: "",
                hints = doc.getLong("hints")?.toInt() ?: 0,
                doubleHints = doc.getLong("doubleHints")?.toInt() ?: 0,
                words = doc.getLong("words")?.toInt() ?: 0
            )

            "MINI" -> GameResult.MiniResult(
                puzzleId = puzzleId,
                puzzleDate = puzzleDate,
                submitDate = submitDate,
                succeeded = succeeded,
                duration = Duration.parse(doc.getString("duration") ?: "")
            )

            else -> null
        }
    }

}