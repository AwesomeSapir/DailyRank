package com.sapreme.dailyrank.parser.cases

import com.sapreme.dailyrank.data.model.GameResult
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

val today: LocalDate = LocalDate.now()

data class Case(
    val raw: String,
    val expected: GameResult
)

val wordleCases = listOf(
    Case(
        """
            Wordle 1,450 4/6

            🟨⬛⬛🟨⬛
            ⬛🟩🟩🟩⬛
            ⬛🟩🟩🟩🟩
            🟩🟩🟩🟩🟩
        """.trimIndent(),
        GameResult.WordleResult(
            puzzleId = 1450,
            submitDate = today,
            succeeded = true,
            attempts = 4
        )
    ),
    Case(
        """
            Wordle 1,450 4/6

            🟨⬛⬛🟨⬛
            ⬛🟩🟩🟩⬛
            ⬛🟩🟩🟩🟩
            🟩🟩🟩🟩🟩
        """.trimIndent(),
        GameResult.WordleResult(
            puzzleId = 1450,
            submitDate = today,
            succeeded = true,
            attempts = 4
        )
    ),
)

val connectionsCases = listOf(
    Case(
        """
            Connections Puzzle #731
            🟦🟨🟨🟨
            🟨🟨🟨🟨
            🟩🟦🟪🟦
            🟪🟦🟦🟩
            🟦🟪🟦🟦
        """.trimIndent(),
        GameResult.ConnectionsResult(
            puzzleId = 731,
            submitDate = today,
            succeeded = false,
            attempts = 5,
            groupings = 1,
            mistakes = 4
        )
    ),
)


val miniCases = listOf(
    Case(
        "I solved the 5/9/2025 New York Times Mini Crossword in 0:50!",
        GameResult.MiniResult(
            puzzleId = 20250509,
            puzzleDate = LocalDate.of(2025, 5, 9),
            submitDate = today,
            duration = 50.seconds
        )
    ),
)

val strandsCases = listOf(
    Case(
        """
            Strands #457
            “That's proprietary!”
            🔵🔵🟡🔵
            🔵🔵
        """.trimIndent(),
        GameResult.StrandsResult(
            puzzleId = 457,
            submitDate = today,
            succeeded = true,
            title = "That's proprietary!",
            hints = 0,
            doubleHints = 0,
            words = 5,
        )
    ),
)