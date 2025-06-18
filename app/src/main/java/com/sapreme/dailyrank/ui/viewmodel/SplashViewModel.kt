package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Destination { ONBOARDING, MAIN }

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()


    init {
        viewModelScope.launch {
            authManager.ensureAnonymousSignIn()

            val user = authManager.authState.filterNotNull().first()

            val profileExists = playerRepository.playerExists(user.uid)

            _destination.value =
                if (profileExists) {
                    Destination.MAIN
                } else {
                    Destination.ONBOARDING
                }
        }
    }

}