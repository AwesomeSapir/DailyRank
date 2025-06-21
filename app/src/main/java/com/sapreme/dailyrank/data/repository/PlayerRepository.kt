package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun observePlayer(uid: String): Flow<Player?>
    fun observePlayersInGroup(groupId: String): Flow<List<Player>>
    suspend fun playerExists(uid: String): Boolean
    suspend fun createPlayer(player: Player)

}