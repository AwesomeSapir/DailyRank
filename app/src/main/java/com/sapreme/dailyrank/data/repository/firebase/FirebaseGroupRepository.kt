package com.sapreme.dailyrank.data.repository.firebase

import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import com.sapreme.dailyrank.data.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseGroupRepository @Inject constructor(
    private val remote: GroupRemoteDataSource
) : GroupRepository {

    override suspend fun createGroup(name: String, creatorId: String): String =
        remote.createGroup(name, creatorId)

    override suspend fun joinGroup(groupId: String, userId: String) =
        remote.joinGroup(groupId, userId)

    override suspend fun leaveGroup(groupId: String, userId: String) =
        remote.leaveGroup(groupId, userId)

    override fun observeGroups(uid: String): Flow<List<Group>> =
        remote.observeGroups(uid).map { list -> list.map { it.toDomain() } }

}