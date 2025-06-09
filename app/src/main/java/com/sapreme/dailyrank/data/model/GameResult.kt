package com.sapreme.dailyrank.data.model

import java.time.LocalDate
import kotlin.time.Duration

sealed interface GameResult {
    val puzzleId: Int
    val date: LocalDate
    val succeeded: Boolean

    data class WordleResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int
    ) : GameResult

    data class ConnectionsResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean,
        val attempts: Int,
        val mistakes: Int
    ) : GameResult

    data class StrandsResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean = true,
        val title: String,
        val hints: Int,
        val doubleHints: Int,
        val words: Int
    ) : GameResult

    data class MiniResult(
        override val puzzleId: Int,
        override val date: LocalDate,
        override val succeeded: Boolean = true,
        val duration: Duration
    ) : GameResult
}