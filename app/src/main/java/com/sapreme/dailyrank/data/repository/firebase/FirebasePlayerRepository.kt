package com.sapreme.dailyrank.data.repository.firebase

import com.google.firebase.auth.FirebaseAuth
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.PlayerRemoteDataSource
import com.sapreme.dailyrank.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebasePlayerRepository @Inject constructor(
    private val remote: PlayerRemoteDataSource,
    private val auth: FirebaseAuth
) : PlayerRepository {

    private val uid: String
        get() = auth.currentUser!!.uid

    override fun observeCurrentPlayer(): Flow<Player?> {
        return remote.observePlayer(uid)
    }

    override suspend fun isPlayerExists(uid: String): Boolean = remote.doesPlayerExist(uid)

    override suspend fun createPlayer(nickname: String) = remote.createPlayer(uid, nickname)

    override suspend fun joinGroup(groupId: String) = remote.joinGroup(uid, groupId)

    override suspend fun leaveGroup(groupId: String) = remote.leaveGroup(uid, groupId)

    override suspend fun createGroup(name: String): String {
        TODO("Not yet implemented")
    }

    override fun observeGroups(): Flow<List<Group>> {
        TODO("Not yet implemented")
    }

}