package com.sapreme.dailyrank.preview

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.model.GameResultFilter
import com.sapreme.dailyrank.data.repository.GameResultRepository
import java.time.LocalDate
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

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

    companion object {
        val wordleResults = listOf(
            GameResult.WordleResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 1023,
                attempts = 1,
                succeeded = true
            ),
            GameResult.WordleResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 1023,
                attempts = 4,
                succeeded = true
            ),
            GameResult.WordleResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 1023,
                attempts = 6,
                succeeded = false
            )
        )

        val connectionsResults = listOf(
            GameResult.ConnectionsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 715,
                attempts = 4,
                groupings = 4,
                mistakes = 0,
                succeeded = true
            ),
            GameResult.ConnectionsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 715,
                attempts = 6,
                groupings = 4,
                mistakes = 2,
                succeeded = true
            ),

            GameResult.ConnectionsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleId = 715,
                attempts = 6,
                groupings = 2,
                mistakes = 4,
                succeeded = false
            )
        )

        val strandsResults = listOf(
            GameResult.StrandsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                title = "At Long Last",
                puzzleId = 449,
                words = 6,
                hints = 0,
                doubleHints = 0
            ),
            GameResult.StrandsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                title = "At Long Last",
                puzzleId = 449,
                words = 6,
                hints = 2,
                doubleHints = 0
            ),

            GameResult.StrandsResult(
                submitDate = LocalDate.of(2025, 6, 10),
                title = "At Long Last",
                puzzleId = 449,
                words = 6,
                hints = 2,
                doubleHints = 1
            )
        )

        val miniResults = listOf(
            GameResult.MiniResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleDate = LocalDate.of(2025, 6, 10),
                puzzleId = 20250610,
                duration = 1.minutes + 35.seconds
            ),
            GameResult.MiniResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleDate = LocalDate.of(2025, 6, 10),
                puzzleId = 20250610,
                duration = 0.minutes + 7.seconds
            ),
            GameResult.MiniResult(
                submitDate = LocalDate.of(2025, 6, 10),
                puzzleDate = LocalDate.of(2025, 6, 10),
                puzzleId = 20250610,
                duration = 1.hours + 5.minutes + 7.seconds
            )
        )
    }
}