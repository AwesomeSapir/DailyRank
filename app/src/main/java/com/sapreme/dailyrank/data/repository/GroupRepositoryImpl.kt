package com.sapreme.dailyrank.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource

class GroupRepositoryImpl(
    private val remote: GroupRemoteDataSource = GroupRemoteDataSource(Firebase.firestore)
) : GroupRepository{

    override suspend fun createGroup(name: String, creatorId: String): String {
        return remote.createGroup(name, creatorId)
    }

    override suspend fun joinGroup(groupId: String, userId: String) {
        remote.joinGroup(groupId, userId)
    }

    override fun getMyGroups(userId: String) = remote.observeUserGroups(userId)

}