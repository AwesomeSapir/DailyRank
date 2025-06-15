package com.sapreme.dailyrank.data.remote

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import com.sapreme.dailyrank.data.model.GameResult

interface GameResultRemoteDataSource {

    suspend fun getUserGameResults(userId: String): List<GameResult>
    suspend fun publishUserGameResult(userId: String, gameResult: GameResult)

}