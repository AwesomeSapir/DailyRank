package com.sapreme.dailyrank.data.parser

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.impl.ConnectionsResultParser
import com.sapreme.dailyrank.data.parser.impl.MiniResultParser
import com.sapreme.dailyrank.data.parser.impl.StrandsResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import java.time.LocalDate

object GameResultParserFactory {

    fun from(raw:String): GameResultParser<out GameResult>? = when {
        raw.startsWith("Wordle") -> WordleResultParser()
        raw.startsWith("Connections") -> ConnectionsResultParser()
        raw.startsWith("Strands") -> StrandsResultParser()
        raw.contains("New York Times Mini Crossword") -> MiniResultParser()
        else -> null
    }
}