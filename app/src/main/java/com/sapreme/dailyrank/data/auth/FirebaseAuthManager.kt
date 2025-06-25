package com.sapreme.dailyrank.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth
) {

    val authState: Flow<AuthStatus> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(
                auth.currentUser?.uid?.let { AuthStatus.Authenticated(it) }
                    ?: AuthStatus.Unauthenticated
            )
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    fun uid(): String? = auth.currentUser?.uid

    suspend fun signInAnonymously(): Result<String> = runCatching {
        auth.signInAnonymously().await().user?.uid ?: error("Anonymous sign-in returned null user")
    }

    suspend fun signInWithGoogle(idToken: String): Result<String> = runCatching {
        val cred = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(cred).await().user?.uid
            ?: error("Google sign-in returned null user")
    }

    suspend fun signOut() = runCatching {
        auth.signOut()
    }.getOrElse { throw CancellationException(it.message, it) }
}