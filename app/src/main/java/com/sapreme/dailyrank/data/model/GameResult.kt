package com.sapreme.dailyrank.data.model

import java.time.LocalDate
import kotlin.time.Duration

sealed interface GameResult {
    val puzzleId: Int
    val date: LocalDate
    val type: Type
    val succeeded: Boolean

    data class WordleResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int
    ) : GameResult {
        override val type = Type.WORDLE
    }

    data class ConnectionsResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int,
        val groupings: Int,
        val mistakes: Int
    ) : GameResult {
        override val type = Type.CONNECTIONS
    }

    data class StrandsResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean = true,
        val title: String,
        val hints: Int,
        val doubleHints: Int,
        val words: Int
    ) : GameResult{
        override val type = Type.STRANDS
    }

    data class MiniResult(
        override val puzzleId: Int,
        override val date: LocalDate,
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