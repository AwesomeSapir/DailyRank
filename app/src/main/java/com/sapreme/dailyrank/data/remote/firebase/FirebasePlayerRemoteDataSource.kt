package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.mapper.toDomain
import com.sapreme.dailyrank.data.mapper.toDto
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.PlayerRemoteDataSource
import com.sapreme.dailyrank.ui.util.toFirestoreTimestamp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class FirebasePlayerRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : PlayerRemoteDataSource {

    override fun observePlayer(uid: String) = callbackFlow {
        val sub = firestore.collection("players")
            .document(uid)
            .addSnapshotListener { snap, err ->
                if (err != null) close(err)
                else trySend(snap?.toObject(PlayerDto::class.java)?.toDomain())
            }
        awaitClose { sub.remove() }
    }

    override suspend fun createPlayerIfNotExists(uid: String, nickname: String):Boolean {
        val doc = firestore.collection("players")
            .document(uid)
            .get()
            .await()
        if (doc.exists()) return false

        val dto: PlayerDto = Player(uid, nickname, LocalDate.now(), emptyList()).toDto()
        firestore.collection("players").document(uid).set(dto).await()
        return true
    }

    override suspend fun joinGroup(uid: String, groupId: String) {
        firestore.runBatch { b ->
            b.update(firestore.collection("players").document(uid), "groups", FieldValue.arrayUnion(groupId))
            b.update(firestore.collection("groups").document(groupId), "members.$uid", "member")
        }.await()
    }

    override suspend fun leaveGroup(uid: String, groupId: String) {
        firestore.runBatch { b ->
            b.update(firestore.collection("players").document(uid), "groups", FieldValue.arrayRemove(groupId))
            b.update(firestore.collection("groups").document(groupId), "members.$uid", FieldValue.delete())
        }.await()
    }
}