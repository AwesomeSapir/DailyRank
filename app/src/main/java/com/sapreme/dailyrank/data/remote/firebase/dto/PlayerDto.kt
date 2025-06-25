package com.sapreme.dailyrank.data.remote.firebase.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.ui.util.toLocalDate

data class PlayerDto(
    @DocumentId var uid: String = "",
    var nickname: String = "",
    var groups: List<String> = emptyList(),
    var createdAt: Timestamp = Timestamp.now()
) {
    fun toDomain(): Player = Player(
        uid = uid,
        nickname = nickname,
        createdAt = createdAt.toLocalDate(),
        groups = groups
    )
}