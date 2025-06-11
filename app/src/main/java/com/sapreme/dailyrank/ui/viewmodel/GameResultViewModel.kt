package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.repository.GameResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
open class GameResultViewModel @Inject constructor(
    private val repo: GameResultRepository
) : ViewModel() {

    private val _parsed = MutableStateFlow<GameResult?>(null)
    open val parsed: StateFlow<GameResult?> = _parsed

    fun parse(raw: String){
        viewModelScope.launch {
            val result = repo.submitResult(raw, LocalDate.now()).getOrNull()
            _parsed.value = result
        }
    }
}
