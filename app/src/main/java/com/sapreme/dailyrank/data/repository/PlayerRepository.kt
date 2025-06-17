package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun observeCurrentPlayer(): Flow<Player?>
    suspend fun createPlayerIfNotExists(nickname: String): Boolean
    suspend fun joinGroup(groupId: String)
    suspend fun leaveGroup(groupId: String)
    suspend fun createGroup(name: String): String
    fun observeGroups(): Flow<List<Group>>
}