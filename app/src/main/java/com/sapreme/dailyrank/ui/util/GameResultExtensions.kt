package com.sapreme.dailyrank.ui.util

import com.sapreme.dailyrank.R
import com.sapreme.dailyrank.data.model.GameResult
import java.time.format.DateTimeFormatter

fun GameResult.puzzleLabel(): String = when (this) {
    is GameResult.WordleResult      -> "#%,d".format(puzzleId)
    is GameResult.ConnectionsResult -> "#%,d".format(puzzleId)
    is GameResult.StrandsResult     -> "#%,d".format(puzzleId)
    is GameResult.MiniResult -> puzzleDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}

fun GameResult.Type.iconRes(): Int = when (this) {
    GameResult.Type.WORDLE -> R.drawable.ic_wordle
    GameResult.Type.CONNECTIONS -> R.drawable.ic_connections
    GameResult.Type.STRANDS -> R.drawable.ic_strands
    GameResult.Type.MINI -> R.drawable.ic_mini
}