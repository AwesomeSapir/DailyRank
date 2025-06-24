package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.sapreme.dailyrank.data.remote.InviteRemoteDataSource
import com.sapreme.dailyrank.util.CodeGenerator
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseInviteRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : InviteRemoteDataSource {

    private fun codeDoc(code: String) = firestore.collection("codes").document(code)
    private fun groupDoc(groupId: String) = firestore.collection("groups").document(groupId)

    override suspend fun createCode(groupId: String): String =
        firestore.runTransaction { transaction ->
            var code: String
            do {
                code = CodeGenerator.newCode(
                    alphabet = CodeGenerator.UPPERCASE + CodeGenerator.DIGITS,
                    length = 6
                )
            } while (transaction.get(codeDoc(code)).exists())
            transaction.set(codeDoc(code), mapOf("groupId" to groupId))
            transaction.update(groupDoc(groupId), "accessCode", code)
            return@runTransaction code
        }.await()

    override suspend fun resolve(code: String): String =
        codeDoc(code).get().await().getString("groupId")
            ?: throw IllegalArgumentException("Code not found")

    override suspend fun fetchCode(groupId: String): String =
        groupDoc(groupId).get().await().getString("accessCode")
            ?: throw IllegalArgumentException("Code not found")
}