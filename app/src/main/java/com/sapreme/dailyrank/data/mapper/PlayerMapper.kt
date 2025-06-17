package com.sapreme.dailyrank.data.mapper

import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.firebase.PlayerDto
import com.sapreme.dailyrank.ui.util.toFirestoreTimestamp
import com.sapreme.dailyrank.ui.util.toLocalDate
import java.time.LocalDate

fun PlayerDto.toDomain(): Player = Player(
    uid = uid,
    nickname = nickname,
    createdAt = createdAt.toLocalDate() ?: LocalDate.now(),
    groups = groups
)

fun Player.toDto(): PlayerDto = PlayerDto(
    uid = uid,
    nickname = nickname,
    createdAt = createdAt.toFirestoreTimestamp(),
    groups = groups
)