package com.sapreme.dailyrank.data.remote

interface MembershipRemoteDataSource {
    suspend fun joinGroup(groupId: String, userId: String)
    suspend fun leaveGroup(groupId: String, userId: String)
}