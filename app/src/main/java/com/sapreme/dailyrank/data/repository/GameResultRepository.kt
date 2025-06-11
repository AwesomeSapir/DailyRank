package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.GameResultParserFactory
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameResultRepository @Inject constructor(){

    fun submitResult(raw: String, date: LocalDate): Result<GameResult> {
        Timber.d("submitResult called")

        val cleaned = when {
            raw.startsWith("Archive") -> {
                Timber.d("Detected archive header, stripping it")
                raw.substringAfter("\n")
            }
            else -> raw
        }

        val parser = GameResultParserFactory.from(cleaned)?: return Result.failure(IllegalArgumentException("Unrecognized format"))
        Timber.i("Found parser: ${parser::class.simpleName}")

        val result = parser.parse(cleaned, date)?: return Result.failure(IllegalArgumentException("Failed to parse game result"))
        Timber.i("Parsing succeeded: $result")

        //remote.saveResult(result)
        // Napier.d("Result saved remotely", tag = "GameResultRepo")
        return Result.success(result)
    }

}