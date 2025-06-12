package com.sapreme.dailyrank.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sapreme.dailyrank.data.model.GameResult
import java.time.LocalDate
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class GameResultProvider : PreviewParameterProvider<GameResult> {

    override val values: Sequence<GameResult> = sequenceOf(
        //Wordle
        GameResult.WordleResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 1023,
            attempts = 1,
            succeeded = true
        ),
        GameResult.WordleResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 1023,
            attempts = 4,
            succeeded = true
        ),
        GameResult.WordleResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 1023,
            attempts = 6,
            succeeded = false
        ),
        //Connections
        GameResult.ConnectionsResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 715,
            attempts = 4,
            groupings = 4,
            mistakes = 0,
            succeeded = true
        ),
        GameResult.ConnectionsResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 715,
            attempts = 6,
            groupings = 4,
            mistakes = 2,
            succeeded = true
        ),

        GameResult.ConnectionsResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 715,
            attempts = 6,
            groupings = 2,
            mistakes = 4,
            succeeded = false
        ),
        //Strands
        GameResult.StrandsResult(
            date = LocalDate.of(2025, 6, 10),
            title = "At Long Last",
            puzzleId = 449,
            words = 6,
            hints = 0,
            doubleHints = 0
        ),
        GameResult.StrandsResult(
            date = LocalDate.of(2025, 6, 10),
            title = "At Long Last",
            puzzleId = 449,
            words = 6,
            hints = 2,
            doubleHints = 0
        ),

        GameResult.StrandsResult(
            date = LocalDate.of(2025, 6, 10),
            title = "At Long Last",
            puzzleId = 449,
            words = 6,
            hints = 2,
            doubleHints = 1
        ),
        //Mini
        GameResult.MiniResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 20250610,
            duration = 1.minutes + 35.seconds
        ),
        GameResult.MiniResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 20250610,
            duration = 0.minutes + 7.seconds
        ),
        GameResult.MiniResult(
            date = LocalDate.of(2025, 6, 10),
            puzzleId = 20250610,
            duration = 1.hours + 5.minutes + 7.seconds
        ),
    )
}