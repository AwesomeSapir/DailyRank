package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter
import java.time.LocalDate

interface GameResultRepository {
    suspend fun parse(raw: String, date: LocalDate): Result<GameResult>
    suspend fun submit(gameResult: GameResult)
    suspend fun getUserResultsBy(
        userId: String,
        filter: GameResultFilter = GameResultFilter()
    ): List<GameResult>
}