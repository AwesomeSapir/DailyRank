package com.sapreme.dailyrank.data.repository.firebase

import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.PlayerRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.dto.toDto
import com.sapreme.dailyrank.data.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebasePlayerRepository @Inject constructor(
    private val remote: PlayerRemoteDataSource,
) : PlayerRepository {

    override fun observePlayer(uid: String): Flow<Player?> =
        remote.observePlayer(uid).map { it?.toDomain() }

    override fun observePlayersInGroup(groupId: String): Flow<List<Player>> =
        remote.observePlayersInGroup(groupId).map { list -> list.map { it.toDomain() } }

    override suspend fun playerExists(uid: String): Boolean =
        remote.getPlayer(uid) != null

    override suspend fun createPlayer(player: Player) =
        remote.createPlayer(player.toDto())
}