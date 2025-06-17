package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRemoteDataSource {

    suspend fun createGroup(name: String, creatorId: String): String

    suspend fun joinGroup(groupId: String, userId: String)

    fun observeUserGroups(userId: String): Flow<List<Group>>

}