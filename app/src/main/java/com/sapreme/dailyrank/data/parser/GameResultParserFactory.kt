package com.sapreme.dailyrank.data.parser

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.impl.ConnectionsResultParser
import com.sapreme.dailyrank.data.parser.impl.MiniResultParser
import com.sapreme.dailyrank.data.parser.impl.StrandsResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import io.github.aakira.napier.Napier
import java.time.LocalDate

object GameResultParserFactory {

    fun from(raw:String): GameResultParser<out GameResult>? {
        Napier.d("Selecting parser for input: \"${raw.take(30)}...\"", tag = "ParserFactory")
        val parser = when {
            raw.startsWith("Wordle") -> WordleResultParser()
            raw.startsWith("Connections") -> ConnectionsResultParser()
            raw.startsWith("Strands") -> StrandsResultParser()
            raw.contains("New York Times Mini Crossword") -> MiniResultParser()
            else -> null
        }

        when {
            parser != null -> Napier.i("Matched parser: ${parser::class.simpleName}", tag = "ParserFactory")
            else -> Napier.w("No parser matched for input", tag = "ParserFactory")
        }

        return parser
    }
}