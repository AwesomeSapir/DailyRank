package com.sapreme.dailyrank.data.parser.impl

import com.sapreme.dailyrank.data.model.GameResult.ConnectionsResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import timber.log.Timber
import java.time.LocalDate

class ConnectionsResultParser : GameResultParser<ConnectionsResult> {

    private val idRegex = Regex("""Puzzle #([\d,]+)""")

    override fun parse(raw: String, date: LocalDate): ConnectionsResult? {
        val rawLines = raw.lineSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .toList().toList()

        if (rawLines.isEmpty() || !rawLines[0].startsWith("Connections", ignoreCase = true)) {
            Timber.w(
                "Share string not recognised (line0=${rawLines.getOrNull(0)}}, size=${rawLines.size}) – aborting"
            )
            return null
        }

        val lines =
            if (rawLines[0].equals("Connections", ignoreCase = true)) rawLines.drop(1) else rawLines

        val idMatch = idRegex.find(lines[0]) ?: run {
            Timber.w("PuzzleId regex failed on: \"${lines[0]}\"")
            return null
        }
        val puzzleId = idMatch.groupValues[1].replace(",", "").toIntOrNull() ?: return null

        val rows = lines.drop(1)
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
            groupings = successes,
            mistakes = mistakes
        )
    }
}