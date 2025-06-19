package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class PlayerDto(
    @DocumentId var uid: String = "",
    var nickname: String = "",
    var groups: List<String> = emptyList(),
    var createdAt: Timestamp = Timestamp.now()
)