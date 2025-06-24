package com.sapreme.dailyrank.data.remote.firebase.dto

import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.util.toFirestoreTimestamp

fun Player.toDto(): PlayerDto = PlayerDto(
    uid = uid,
    nickname = nickname,
    createdAt = createdAt.toFirestoreTimestamp(),
    groups = groups
)

fun Group.toDto() = GroupDto(
    id = id,
    name = name,
    createdBy = createdBy,
    createdAt = createdAt.toFirestoreTimestamp()
)