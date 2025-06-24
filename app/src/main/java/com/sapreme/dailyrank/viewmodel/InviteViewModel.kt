package com.sapreme.dailyrank.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(
    private val repo: GroupRepository
) : ViewModel() {

    data class UiState(val code: String, val qr: ImageBitmap)

    private val _state = MutableStateFlow<UiState?>(null)
    val state = _state.asStateFlow()

    fun load(groupId: String) = viewModelScope.launch {
        val code = repo.getInviteCode(groupId)
        val qr = ImageBitmap(1, 1)
        _state.value = UiState(code, qr)
    }

    fun clear() {
        _state.value = null
    }

}