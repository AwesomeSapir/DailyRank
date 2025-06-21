package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.sapreme.dailyrank.data.remote.PlayerRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.dto.PlayerDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebasePlayerRemoteDataSource(
    private val firestore: FirebaseFirestore
) : PlayerRemoteDataSource {

    private fun doc(uid: String) = firestore.collection("players").document(uid)

    override fun observePlayer(uid: String): Flow<PlayerDto?> =
        callbackFlow {
            val reg = doc(uid).addSnapshotListener { snap, _ ->
                trySend(snap?.toObject<PlayerDto>())
            }
            awaitClose { reg.remove() }
        }

    override fun observePlayersInGroup(groupId: String): Flow<List<PlayerDto>> =
        callbackFlow {
            val reg = firestore.collection("players")
                .whereArrayContains("groups", groupId)
                .addSnapshotListener { snap, _ ->
                    trySend(snap?.toObjects<PlayerDto>() ?: emptyList())
                }
            awaitClose { reg.remove() }
        }

    override suspend fun getPlayer(uid: String): PlayerDto? =
        doc(uid).get().await().toObject()

    override suspend fun createPlayer(dto: PlayerDto) {
        doc(dto.uid).set(dto).await()
    }
}