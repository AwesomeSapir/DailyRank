package com.sapreme.dailyrank.data.remote.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth
) {
    suspend fun ensureLoggedIn(): FirebaseUser {
        return suspendCoroutine { cont ->
            auth.currentUser ?: auth.signInAnonymously()
                .addOnSuccessListener { cont.resume(it.user!!) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }
    }

    fun isSignedIn(): Boolean = auth.currentUser != null

    fun getUserId(): String =
        auth.currentUser?.uid ?: throw IllegalStateException("User is not signed in")

    fun getUser(): FirebaseUser? = auth.currentUser
}