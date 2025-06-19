package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import com.sapreme.dailyrank.data.remote.firebase.dto.GroupDto
import com.sapreme.dailyrank.data.remote.firebase.dto.MemberDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseGroupRemoteDataSource(
    private val firestore: FirebaseFirestore
) : GroupRemoteDataSource {

    private fun groupDoc(gid: String) = firestore.collection("groups").document(gid)
    private fun membersColl(gid: String) = groupDoc(gid).collection("members")

    override suspend fun createGroup(name: String, creatorId: String): String {
        val gid = firestore.collection("groups").document().id
        val now = Timestamp.now()

        firestore.runBatch { batch ->
            batch.set(
                groupDoc(gid),
                GroupDto(
                    id = gid,
                    name = name,
                    createdBy = creatorId,
                    createdAt = now,
                )
            )

            batch.set(
                membersColl(gid).document(creatorId),
                MemberDto(
                    uid = creatorId,
                    joinedAt = now
                )
            )
        }.await()

        return gid
    }

    override suspend fun joinGroup(groupId: String, userId: String) {
        firestore.runBatch { batch ->
            batch.update(groupDoc(groupId), "memberUids", FieldValue.arrayUnion(userId))

            batch.set(
                membersColl(groupId).document(userId), MemberDto(
                    uid = userId,
                    joinedAt = Timestamp.now()
                )
            )
        }.await()
    }

    override suspend fun leaveGroup(groupId: String, userId: String) {
        firestore.runBatch { batch ->
            batch.update(groupDoc(groupId), "memberUids", FieldValue.arrayRemove(userId))

            batch.delete(membersColl(groupId).document(userId))
        }.await()
    }

    override fun observeGroups(userId: String): Flow<List<GroupDto>> =
        callbackFlow {
            val reg = firestore.collection("groups")
                .whereArrayContains("memberUids", userId)
                .addSnapshotListener { snap, _ ->
                    trySend(snap?.toObjects(GroupDto::class.java) ?: emptyList())
                }
            awaitClose { reg.remove() }
        }


}