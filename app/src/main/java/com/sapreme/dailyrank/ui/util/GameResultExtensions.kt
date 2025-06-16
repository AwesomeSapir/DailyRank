package com.sapreme.dailyrank.ui.util

import com.sapreme.dailyrank.data.model.GameResult
import java.time.format.DateTimeFormatter

fun GameResult.puzzleLabel(): String = when (this) {
    is GameResult.WordleResult      -> "#%,d".format(puzzleId)
    is GameResult.ConnectionsResult -> "#%,d".format(puzzleId)
    is GameResult.StrandsResult     -> "#%,d".format(puzzleId)
    is GameResult.MiniResult -> puzzleDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}