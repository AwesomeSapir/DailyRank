package com.sapreme.dailyrank.data.parser

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import java.time.LocalDate

object GameResultParserFactory {
    private val parsers = listOf(
        WordleResultParser(),
    )

    fun from(raw:String): GameResultParser<out GameResult>? = when {
        raw.startsWith("Wordle") -> WordleResultParser()
        raw.startsWith("Connections") -> TODO()
        else -> null
    }
}