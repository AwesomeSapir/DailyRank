package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import io.github.aakira.napier.Napier
import java.time.LocalDate

class GameResultRepository(

) {

    fun submitResult(raw: String, date: LocalDate): Result<GameResult> {
        Napier.d("submitResult called", tag = "GameResultRepo")

        val parser = GameResultParserFactory.from(raw)?: return Result.failure(IllegalArgumentException("Unrecognized format"))
        Napier.i("Found parser: ${parser::class.simpleName}", tag = "GameResultRepo")

        val result = parser.parse(raw, date)?: return Result.failure(IllegalArgumentException("Failed to parse game result"))
        Napier.i("Parsing succeeded: $result", tag = "GameResultRepo")

        //remote.saveResult(result)
        // Napier.d("Result saved remotely", tag = "GameResultRepo")
        return Result.success(result)
    }

}