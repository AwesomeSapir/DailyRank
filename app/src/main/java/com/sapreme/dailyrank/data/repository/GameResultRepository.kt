package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import java.time.LocalDate

class GameResultRepository(

) {

    suspend fun submitResult(raw: String, date: LocalDate): Result<GameResult> {
        val parser = GameResultParserFactory.from(raw)?: return Result.failure(IllegalArgumentException("Unrecognized format"))
        val result = parser.parse(raw, date)?: return Result.failure(IllegalArgumentException("Failed to parse game result"))
        //remote.saveResult(result)
        return Result.success(result)
    }

}