package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class PlayerDto(
    @DocumentId val uid: String,
    val nickname: String,
    val groups: List<String> = emptyList(),
    val createdAt: Timestamp
)
