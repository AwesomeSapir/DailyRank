package com.sapreme.dailyrank.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthManager @Inject constructor(
    private val auth: FirebaseAuth
) {

    private val _authState = MutableStateFlow(auth.currentUser)
    val authState: StateFlow<FirebaseUser?> get() = _authState

    init {
        auth.addAuthStateListener { firebaseAuth -> _authState.value = firebaseAuth.currentUser }
    }

    fun uid(): String? = auth.currentUser?.uid

    suspend fun ensureAnonymousSignIn() {
        if (auth.currentUser == null) {
            auth.signInAnonymously().await()
        }
    }

    fun signOut() = auth.signOut()
}