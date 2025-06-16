package com.sapreme.dailyrank.preview

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter
import com.sapreme.dailyrank.data.repository.GameResultRepository
import java.time.LocalDate

class FakeGameResultRepository(
    private val predefinedResult: GameResult? = null
) : GameResultRepository {

    override suspend fun parse(raw: String, date: LocalDate): Result<GameResult> {
        return predefinedResult?.let { Result.success(it) } ?: Result.failure(
            IllegalArgumentException("No predefined result provided")
        )
    }

    override suspend fun submit(gameResult: GameResult) {

    }

    override suspend fun getUserResultsBy(
        userId: String,
        filter: GameResultFilter
    ): List<GameResult> {
        return emptyList()
    }

}