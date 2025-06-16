package com.sapreme.dailyrank.data.model

import java.time.LocalDate

data class GameResultFilter(
    val type: GameResult.Type? = null,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
)
