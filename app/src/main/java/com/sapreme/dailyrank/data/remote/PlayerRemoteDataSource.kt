package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRemoteDataSource {

    fun observePlayer(uid: String): Flow<Player?>
    suspend fun doesPlayerExist(uid: String): Boolean
    suspend fun createPlayer(uid: String, nickname: String)
    suspend fun joinGroup(uid: String, groupId: String)
    suspend fun leaveGroup(uid: String, groupId: String)

}