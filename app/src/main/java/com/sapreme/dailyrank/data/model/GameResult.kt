package com.sapreme.dailyrank.data.model

import java.time.LocalDate
import kotlin.time.Duration

sealed interface GameResult {
    val puzzleId: Int
    val puzzleDate: LocalDate
    val submitDate: LocalDate
    val type: Type
    val succeeded: Boolean

    data class WordleResult(
        override val puzzleId: Int,
        override val submitDate: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int
    ) : GameResult {
        override val type = Type.WORDLE
        override val puzzleDate: LocalDate = LocalDate.of(2021, 6, 19).plusDays(puzzleId.toLong())
    }

    data class ConnectionsResult(
        override val puzzleId: Int,
        override val submitDate: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int,
        val groupings: Int,
        val mistakes: Int
    ) : GameResult {
        override val type = Type.CONNECTIONS
        override val puzzleDate: LocalDate = LocalDate.of(2023, 6, 11).plusDays(puzzleId.toLong())
    }

    data class StrandsResult(
        override val puzzleId: Int,
        override val submitDate: LocalDate,
        override val succeeded: Boolean = true,
        val title: String,
        val hints: Int,
        val doubleHints: Int,
        val words: Int
    ) : GameResult{
        override val type = Type.STRANDS
        override val puzzleDate: LocalDate = LocalDate.of(2024, 3, 3).plusDays(puzzleId.toLong())
    }

    data class MiniResult(
        override val puzzleId: Int,
        override val puzzleDate: LocalDate,
        override val submitDate: LocalDate,
        override val succeeded: Boolean = true,
        val duration: Duration
    ) : GameResult{
        override val type = Type.MINI
    }

    enum class Type(private val displayName: String) {
        WORDLE("Wordle"),
        CONNECTIONS("Connections"),
        STRANDS("Strands"),
        MINI("Mini");
        override fun toString() = displayName
    }
}