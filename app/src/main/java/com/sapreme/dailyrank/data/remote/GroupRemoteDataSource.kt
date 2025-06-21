package com.sapreme.dailyrank.data.remote

import com.sapreme.dailyrank.data.remote.firebase.dto.GroupDto
import kotlinx.coroutines.flow.Flow

interface GroupRemoteDataSource {

    suspend fun createGroup(name: String, creatorId: String): String

    suspend fun joinGroup(groupId: String, userId: String)

    suspend fun leaveGroup(groupId: String, userId: String)

    fun observeGroup(groupId: String): Flow<GroupDto?>

    fun observeGroups(userId: String): Flow<List<GroupDto>>

}