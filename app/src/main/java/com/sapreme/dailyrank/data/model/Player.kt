package com.sapreme.dailyrank.data.model

import java.time.LocalDate

data class Player(
    val uid: String,
    val nickname: String,
    val createdAt: LocalDate = LocalDate.now(),
    val groups: List<String> = emptyList(),
)
