package com.sapreme.dailyrank.data.parser

import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.parser.impl.ConnectionsResultParser
import com.sapreme.dailyrank.data.parser.impl.MiniResultParser
import com.sapreme.dailyrank.data.parser.impl.StrandsResultParser
import com.sapreme.dailyrank.data.parser.impl.WordleResultParser
import timber.log.Timber

object GameResultParserFactory {

    fun from(raw:String): GameResultParser<out GameResult>? {
        Timber.d("Selecting parser for input: \"${raw.take(30)}...\"")
        val parser = when {
            raw.startsWith("Wordle") -> WordleResultParser()
            raw.startsWith("Connections") -> ConnectionsResultParser()
            raw.startsWith("Strands") -> StrandsResultParser()
            raw.contains("New York Times Mini Crossword") -> MiniResultParser()
            else -> null
        }

        when {
            parser != null -> Timber.i("Matched parser: ${parser::class.simpleName}")
            else -> Timber.w("No parser matched for input")
        }

        return parser
    }
}