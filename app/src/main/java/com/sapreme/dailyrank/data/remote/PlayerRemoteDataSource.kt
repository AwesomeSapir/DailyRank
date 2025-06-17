package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRemoteDataSource {

    fun observePlayer(uid: String): Flow<Player?>
    suspend fun createPlayerIfNotExists(uid: String, nickname: String):Boolean
    suspend fun joinGroup(uid: String, groupId: String)
    suspend fun leaveGroup(uid: String, groupId: String)

}