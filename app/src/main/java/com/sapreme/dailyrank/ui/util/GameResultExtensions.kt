package com.sapreme.dailyrank.ui.util

import com.sapreme.dailyrank.data.model.GameResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun GameResult.puzzleLabel(): String = when (this) {
    is GameResult.WordleResult      -> "#%,d".format(puzzleId)
    is GameResult.ConnectionsResult -> "#%,d".format(puzzleId)
    is GameResult.StrandsResult     -> "#%,d".format(puzzleId)
    is GameResult.MiniResult        -> {
        val parsed = puzzleId.toString().let {
            LocalDate.of(it.substring(0, 4).toInt(), it.substring(4, 6).toInt(), it.substring(6, 8).toInt())
        }
        parsed.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    }
}