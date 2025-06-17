package com.sapreme.dailyrank.data.repository.firebase

import com.sapreme.dailyrank.data.remote.firebase.FirebaseGroupRemoteDataSource
import com.sapreme.dailyrank.data.repository.GroupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseGroupRepository @Inject constructor(
    private val remote: FirebaseGroupRemoteDataSource
) : GroupRepository {

    override suspend fun createGroup(name: String, creatorId: String): String {
        return remote.createGroup(name, creatorId)
    }

    override suspend fun joinGroup(groupId: String, userId: String) {
        remote.joinGroup(groupId, userId)
    }

    override fun getGroups(userId: String) = remote.observeUserGroups(userId)

}