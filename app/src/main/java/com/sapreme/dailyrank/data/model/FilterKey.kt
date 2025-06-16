package com.sapreme.dailyrank.data.model

import java.time.LocalDate

sealed class FilterKey {
    abstract fun toGameResultFilter(): GameResultFilter

    data object Today : FilterKey() {
        override fun toGameResultFilter(): GameResultFilter {
            val today = LocalDate.now()
            return GameResultFilter(
                startDate = today,
                endDate = today
            )
        }
    }

    data class Weekly(val type: GameResult.Type) : FilterKey() {
        override fun toGameResultFilter(): GameResultFilter {
            val today = LocalDate.now()
            return GameResultFilter(
                startDate = today.minusDays(6),
                endDate = today,
                type = type
            )
        }
    }

    data class Custom(val filter: GameResultFilter) : FilterKey() {
        override fun toGameResultFilter() = filter
    }
}