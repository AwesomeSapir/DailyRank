package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.auth.AuthResult
import com.sapreme.dailyrank.data.auth.AuthStatus
import com.sapreme.dailyrank.data.auth.FirebaseAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuthManager,
) : ViewModel() {

    data class UiState(
        val authResult: AuthResult? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val authStatus: StateFlow<AuthStatus> =
        auth.authState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthStatus.Unauthenticated
        )

    fun signInAsGuest() = viewModelScope.launch {
        _uiState.value = UiState(AuthResult.Loading)
        _uiState.value = UiState(
            auth.signInAnonymously().fold(
                onSuccess = { AuthResult.Success(it) },
                onFailure = { AuthResult.Error(it.message ?: "Anonymous sign-in failed") }
            )
        )
    }

    fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        if (idToken.isBlank()) {
            _uiState.value = UiState(AuthResult.Error("Google sign-in cancelled"))
            return@launch
        }
        _uiState.value = UiState(AuthResult.Loading)
        _uiState.value = UiState(
            auth.signInWithGoogle(idToken).fold(
                onSuccess = { AuthResult.Success(it) },
                onFailure = { AuthResult.Error(it.message ?: "Google sign-in failed") }
            )
        )
    }
}