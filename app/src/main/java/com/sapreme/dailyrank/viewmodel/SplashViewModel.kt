package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.auth.AuthStatus
import com.sapreme.dailyrank.data.auth.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Destination { ONBOARDING, MAIN, SIGN_IN }

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            when (val status = authManager.authState.first()) {
                is AuthStatus.Authenticated -> {
                    val profileExists = playerRepository.playerExists(status.uid)
                    _destination.value =
                        if (profileExists) {
                            Destination.MAIN
                        } else {
                            Destination.ONBOARDING
                        }
                }

                AuthStatus.Unauthenticated ->
                    _destination.value = Destination.SIGN_IN
            }
        }
    }

}