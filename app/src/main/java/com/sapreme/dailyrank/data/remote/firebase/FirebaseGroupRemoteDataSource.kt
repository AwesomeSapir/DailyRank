package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.dto.GroupDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseGroupRemoteDataSource(
    private val firestore: FirebaseFirestore
) : GroupRemoteDataSource {

    private fun groupDoc(gid: String) = firestore.collection("groups").document(gid)

    override suspend fun createGroup(name: String, creatorId: String): String {
        val gid = firestore.collection("groups").document().id
        val now = Timestamp.now()

        groupDoc(gid).set(
            GroupDto(
                id = gid,
                name = name,
                createdBy = creatorId,
                createdAt = now,
                memberIds = listOf(creatorId)
            )
        ).await()

        return gid
    }

    override suspend fun joinGroup(
        groupId: String,
        userId: String
    ) { //TODO Change to enqueueAddMember to allow batch firestore writes
        groupDoc(groupId).update("memberIds", FieldValue.arrayUnion(userId)).await()
    }

    override suspend fun leaveGroup(
        groupId: String,
        userId: String
    ) { //TODO Change to enqueueRemoveMember to allow batch firestore writes
        groupDoc(groupId).update("memberIds", FieldValue.arrayRemove(userId)).await()
    }

    override fun observeGroup(groupId: String): Flow<GroupDto?> =
        callbackFlow {
            val reg = groupDoc(groupId)
                .addSnapshotListener { snap, err ->
                    if (err != null) {
                        close(err)
                    } else {
                        trySend(snap?.toObject<GroupDto>())
                    }
                }

            awaitClose { reg.remove() }
        }

    override fun observeGroups(userId: String): Flow<List<GroupDto>> =
        callbackFlow {
            val reg = firestore.collection("groups")
                .whereArrayContains("memberIds", userId)
                .addSnapshotListener { snap, err ->
                    if (err != null) {
                        close(err)
                    } else {
                        trySend(snap?.toObjects<GroupDto>() ?: emptyList())
                    }
                }
            awaitClose { reg.remove() }
        }
}