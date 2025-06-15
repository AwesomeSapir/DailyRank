package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.GameResult
import java.time.LocalDate

interface GameResultRepository {
    suspend fun parse(raw: String, date: LocalDate): Result<GameResult>
    suspend fun submit(gameResult: GameResult)
}