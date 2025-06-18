package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.PlayerRepository
import com.sapreme.dailyrank.ui.util.avatarUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    data class UiState(
        val nickname: String = "",
        val avatarUrl: String = "",
        val isSaving: Boolean = false,
        val error: String? = null
    ) {
        val continueEnabled: Boolean get() = nickname.isNotBlank() && !isSaving
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _done = MutableStateFlow(false)
    val done: StateFlow<Boolean> = _done.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = authManager.uid()
            _state.value = _state.value.copy(
                avatarUrl = avatarUrl(uid ?: "guest")
            )
        }
    }

    fun onNicknameChange(newName: String) {
        _state.value = _state.value.copy(nickname = newName.take(25), error = null)
    }

    fun savePlayer() = viewModelScope.launch {
        val current = _state.value
        if (!current.continueEnabled) return@launch

        _state.value = current.copy(isSaving = true)

        try {
            val uid = authManager.uid() ?: error("Missing auth UID")
            val player = Player(
                uid = uid,
                nickname = current.nickname.trim(),
                createdAt = LocalDate.now()
            )
            playerRepository.createPlayer(player)
            _done.value = true
        } catch (e: Exception) {
            _state.value = current.copy(
                isSaving = false,
                error = e.message ?: "Unknown error"
            )
        }
    }

}