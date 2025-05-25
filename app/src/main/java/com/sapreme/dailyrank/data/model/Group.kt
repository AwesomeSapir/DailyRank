package com.sapreme.dailyrank.data.model

import com.google.firebase.Timestamp

data class Group(
    val id: String = "",
    val name: String = "",
    val createdBy: String = "",
    val members: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now()
)
