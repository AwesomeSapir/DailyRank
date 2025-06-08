package com.sapreme.dailyrank.data.parser

import com.sapreme.dailyrank.data.model.GameResult
import java.time.LocalDate

interface GameResultParser<T: GameResult> {
    fun parse(raw: String, date: LocalDate): T?
}