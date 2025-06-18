package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun observePlayer(uid: String): Flow<Player?>
    suspend fun playerExists(uid: String): Boolean
    suspend fun createPlayer(player: Player)

}