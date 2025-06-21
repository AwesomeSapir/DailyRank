package com.sapreme.dailyrank.data.repository

import com.sapreme.dailyrank.data.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun createGroup(name: String, creatorId: String): String
    suspend fun joinGroup(groupId: String, userId: String)
    suspend fun leaveGroup(groupId: String, userId: String)
    fun observeGroup(groupId: String): Flow<Group?>
    fun observeGroups(uid: String): Flow<List<Group>>
}