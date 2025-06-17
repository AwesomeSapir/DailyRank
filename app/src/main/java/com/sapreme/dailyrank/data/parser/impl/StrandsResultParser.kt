package com.sapreme.dailyrank.data.parser.impl

import com.sapreme.dailyrank.data.model.GameResult.StrandsResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import java.time.LocalDate

class StrandsResultParser: GameResultParser<StrandsResult> {

    private val idRegex    = Regex("""Strands #([\d,]+)""")
    private val titleRegex = Regex("""“([^”]+)”""")
    private val hintCp = "💡".codePoints().toArray().first()
    private val wordCp = "🔵".codePoints().toArray().first()

    override fun parse(raw: String, date: LocalDate): StrandsResult? {
        val lines = raw
            .lineSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .toList()
        if (lines.size < 3) return null

        val puzzleId = idRegex.find(lines[0])
            ?.groupValues?.get(1)
            ?.toIntOrNull() ?: return null

        val title = titleRegex.find(lines[1])
            ?.groupValues?.get(1)
            ?: return null

        val grid = lines
            .drop(2)
            .joinToString(separator = "")

        val doubleHints = Regex("💡💡").findAll(grid).count()

        val totalHints = grid
            .codePoints()
            .toArray()
            .count { it == hintCp }

        val hints = totalHints - 2 * doubleHints

        val words = grid
            .codePoints()
            .toArray()
            .count { it == wordCp }

        return StrandsResult(
            puzzleId = puzzleId,
            submitDate = date,
            title = title,
            hints = hints,
            doubleHints = doubleHints,
            words = words
        )
    }
}