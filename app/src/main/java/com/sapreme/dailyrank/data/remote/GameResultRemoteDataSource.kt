package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter

interface GameResultRemoteDataSource {

    suspend fun getUserGameResultsBy(
        userId: String,
        filter: GameResultFilter = GameResultFilter()
    ): List<GameResult>
    suspend fun publishUserGameResult(userId: String, gameResult: GameResult)

}