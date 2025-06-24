package com.sapreme.dailyrank.data.repository.firebase

import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.InviteRemoteDataSource
import com.sapreme.dailyrank.data.remote.MembershipRemoteDataSource
import com.sapreme.dailyrank.data.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseGroupRepository @Inject constructor(
    private val remote: GroupRemoteDataSource,
    private val remoteMembership: MembershipRemoteDataSource,
    private val remoteInvite: InviteRemoteDataSource
) : GroupRepository {

    override suspend fun createGroup(name: String, creatorId: String): String {
        val groupId = remote.createGroup(name, creatorId)
        remoteMembership.joinGroup(groupId, creatorId)
        remoteInvite.createCode(groupId)
        return groupId
    }

    override suspend fun getInviteCode(groupId: String): String =
        remoteInvite.fetchCode(groupId)

    override suspend fun joinGroup(code: String, userId: String) =
        remoteMembership.joinGroup(remoteInvite.resolve(code), userId)

    override suspend fun leaveGroup(groupId: String, userId: String) =
        remoteMembership.leaveGroup(groupId, userId)

    override fun observeGroup(groupId: String): Flow<Group?> =
        remote.observeGroup(groupId).map { it?.toDomain() }

    override fun observeGroups(uid: String): Flow<List<Group>> =
        remote.observeGroups(uid).map { list -> list.map { it.toDomain() } }

}