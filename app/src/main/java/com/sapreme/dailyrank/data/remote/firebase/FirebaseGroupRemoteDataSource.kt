package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.model.Group
import com.sapreme.dailyrank.data.remote.GroupRemoteDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseGroupRemoteDataSource(
    firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
): GroupRemoteDataSource {

    private val groupsCol = firestore.collection("groups")

    override suspend fun createGroup(name: String, creatorId: String): String {
        val docRef = groupsCol.document()
        val id = docRef.id

        val group = Group(
            id        = id,
            name      = name,
            createdBy = creatorId,
            members   = listOf(creatorId),
            createdAt = Timestamp.now()
        )

        docRef.set(group).await()
        return id
    }

    override suspend fun joinGroup(groupId: String, userId: String) {
        groupsCol.document(groupId)
            .update("members", com.google.firebase.firestore.FieldValue.arrayUnion(userId))
            .await()
    }

    override fun observeUserGroups(userId: String): Flow<List<Group>> = callbackFlow {
        val subscription = groupsCol
            .whereArrayContains("members", userId)
            .addSnapshotListener { snap, err ->
                if (err != null) { close(err); return@addSnapshotListener }
                val groups = snap?.documents?.mapNotNull { it.toObject(Group::class.java)?.copy(id=it.id) }
                trySend(groups ?: emptyList())
            }
        awaitClose { subscription.remove() }
    }


}