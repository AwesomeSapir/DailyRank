package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(name: String, creatorId: String): String
    suspend fun joinGroup(groupId: String, userId: String)
    fun getGroups(userId: String): Flow<List<Group>>
}