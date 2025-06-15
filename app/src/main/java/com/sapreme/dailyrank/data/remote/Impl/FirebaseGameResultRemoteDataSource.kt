package com.sapreme.dailyrank.data.remote.Impl

import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.remote.GameResultRemoteDataSource
import timber.log.Timber

class FirebaseGameResultRemoteDataSource (
    private val firestore: FirebaseFirestore
): GameResultRemoteDataSource{

    override suspend fun getUserGameResults(userId: String): List<GameResult> {
        return emptyList<GameResult>()
    }

    override suspend fun publishUserGameResult(userId: String, gameResult: GameResult) {
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