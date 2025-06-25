package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.remote.MembershipRemoteDataSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseMembershipRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
): MembershipRemoteDataSource {

    override suspend fun joinGroup(groupId: String, userId: String) {
        firestore.runBatch { batch ->
            batch.update(firestore.collection("groups").document(groupId), "memberIds", FieldValue.arrayUnion(userId))
            batch.update(firestore.collection("players").document(userId), "groups", FieldValue.arrayUnion(groupId))
        }.await()
    }

    override suspend fun leaveGroup(groupId: String, userId: String) {
        firestore.runBatch { batch ->
            batch.update(firestore.collection("groups").document(groupId), "memberIds", FieldValue.arrayRemove(userId))
            batch.update(firestore.collection("players").document(userId), "groups", FieldValue.arrayRemove(groupId))
        }.await()
    }
}