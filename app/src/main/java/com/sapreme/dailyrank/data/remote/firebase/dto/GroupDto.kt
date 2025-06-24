package com.sapreme.dailyrank.data.remote.firebase.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.util.toLocalDate

data class GroupDto(
    @DocumentId var id: String = "",
    var name: String = "",
    var createdBy: String = "",
    var createdAt: Timestamp = Timestamp.now(),
    var memberIds: List<String> = listOf(createdBy)
) {
    fun toDomain() = Group(
        id = id,
        name = name,
        createdBy = createdBy,
        createdAt = createdAt.toLocalDate()
    )
}
