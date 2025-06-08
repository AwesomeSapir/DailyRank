package com.sapreme.dailyrank.data.parser.impl

import com.sapreme.dailyrank.data.model.GameResult.WordleResult
import com.sapreme.dailyrank.data.parser.GameResultParser
import java.time.LocalDate

class WordleResultParser: GameResultParser<WordleResult> {
    override fun parse(raw: String, date: LocalDate): WordleResult? {
        //TODO
        return WordleResult(
            puzzleId = 1,
            date = LocalDate.now(),
            succeeded = true,
            attempts = 3
        )
    }
}