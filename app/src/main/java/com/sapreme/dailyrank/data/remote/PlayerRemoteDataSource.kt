package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.remote.firebase.dto.PlayerDto
import kotlinx.coroutines.flow.Flow

interface PlayerRemoteDataSource {

    fun observePlayer(uid: String): Flow<PlayerDto?>
    fun observePlayersInGroup(groupId: String): Flow<List<PlayerDto>>
    suspend fun getPlayer(uid: String): PlayerDto?
    suspend fun createPlayer(dto: PlayerDto)
    suspend fun joinGroup(uid: String, groupId: String)
    suspend fun leaveGroup(uid: String, groupId: String)

}