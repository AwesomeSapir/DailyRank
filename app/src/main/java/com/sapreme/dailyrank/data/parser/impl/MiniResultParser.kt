package com.sapreme.dailyrank.data.parser.impl

import com.sapreme.dailyrank.data.model.GameResult.MiniResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class MiniResultParser: GameResultParser<MiniResult> {

    private val headerRegex = Regex(
        """I solved the (\d{1,2}/\d{1,2}/\d{4}) New York Times Mini Crossword in (\d+):(\d{2})!"""
    )

    override fun parse(raw: String, date: LocalDate): MiniResult? {
        val header = raw.lineSequence()
            .firstOrNull { it.isNotBlank() }
            ?.trim() ?: return null

        val match = headerRegex.find(header) ?: return null
        val(dateStr, minStr, secStr) = match.destructured

        val parsedDate=LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("M/d/yyyy"))
        val puzzleId =  parsedDate.year * 10000 +
                        parsedDate.monthValue * 100 +
                        parsedDate.dayOfMonth

        val duration: Duration = minStr.toLong().minutes + secStr.toLong().seconds

        return MiniResult(
            puzzleId = puzzleId,
            date = date,
            duration = duration
        )
    }
}