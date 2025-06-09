package com.sapreme.dailyrank.data.parser.impl

import android.util.Log
import com.sapreme.dailyrank.data.model.GameResult.ConnectionsResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import java.time.LocalDate

class ConnectionsResultParser : GameResultParser<ConnectionsResult> {

    private val idRegex = Regex("""Puzzle #([\d,]+)""")

    override fun parse(raw: String, date: LocalDate): ConnectionsResult? {
        val lines = raw.lineSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .toList()

        if (lines.size < 3 || lines[0] != "Connections") return null

        val idMatch = idRegex.find(lines[1]) ?: return null
        val puzzleId = idMatch.groupValues[1].toInt()

        val rows = lines.drop(2)
        val attempts = rows.size
        val successes = rows.count { row ->
            val cps = row.codePoints().toArray()
            cps.all { it == cps[0] }
        }
        val mistakes = attempts - successes
        val succeeded = successes == 4

        return ConnectionsResult(
            puzzleId = puzzleId,
            date = date,
            succeeded = succeeded,
            attempts = attempts,
            mistakes = mistakes
        )
    }


}