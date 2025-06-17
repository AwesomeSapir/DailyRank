package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.model.Player
import com.sapreme.dailyrank.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repo: PlayerRepository
): ViewModel() {
    val player: StateFlow<Player?> = repo.observeCurrentPlayer().stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun createPlayer(nickname: String) = viewModelScope.launch {
        repo.createPlayerIfNotExists(nickname)
    }

    fun joinGroup(groupId: String) = viewModelScope.launch {
        repo.joinGroup(groupId)
    }

    fun leaveGroup(groupId: String) = viewModelScope.launch {
        repo.leaveGroup(groupId)
    }
}