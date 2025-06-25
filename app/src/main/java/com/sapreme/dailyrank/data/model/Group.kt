package com.sapreme.dailyrank.data.model

import java.time.LocalDate

data class Group(
    val id: String = "",
    val name: String = "",
    val createdBy: String = "",
    val createdAt: LocalDate = LocalDate.now()
)
