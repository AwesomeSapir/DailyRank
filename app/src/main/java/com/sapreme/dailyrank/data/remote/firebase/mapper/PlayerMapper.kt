package com.sapreme.dailyrank.data.remote.firebase.mapper

import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.firebase.PlayerDto
import com.sapreme.dailyrank.ui.util.toFirestoreTimestamp
import com.sapreme.dailyrank.ui.util.toLocalDate

fun PlayerDto.toDomain(): Player = Player(
    uid = uid,
    nickname = nickname,
    createdAt = createdAt.toLocalDate(),
    groups = groups
)

fun Player.toDto(): PlayerDto = PlayerDto(
    uid = uid,
    nickname = nickname,
    createdAt = createdAt.toFirestoreTimestamp(),
    groups = groups
)