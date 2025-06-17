package com.sapreme.dailyrank.data.parser.impl

import com.sapreme.dailyrank.data.model.GameResult.WordleResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import java.time.LocalDate

class WordleResultParser: GameResultParser<WordleResult> {

    private val headerRegex = Regex("""Wordle ([\d,]+) ([1-6X])/6""")

    override fun parse(raw: String, date: LocalDate): WordleResult? {
        val header = raw.lines()
            .firstOrNull { it.isNotBlank() }
            ?.trim() ?: return null

        val match = headerRegex.find(header) ?: return null
        val (idStr, result) = match.destructured

        val puzzleId = idStr.replace(",", "").toIntOrNull() ?: return null

        val succeeded = result != "X"
        val attempts = if(result == "X") 6 else result.toInt()

        return WordleResult(
            puzzleId = puzzleId,
            submitDate = date,
            succeeded = succeeded,
            attempts = attempts
        )
    }
}