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

enum class SubmissionState { Idle, Loading, Success, Error }

@HiltViewModel
open class GameResultViewModel @Inject constructor(
    private val repo: GameResultRepository
) : ViewModel() {

    private val _parsed = MutableStateFlow<GameResult?>(null)
    open val parsed: StateFlow<GameResult?> = _parsed

    private val _submissionState = MutableStateFlow(SubmissionState.Idle)
    val submissionState: StateFlow<SubmissionState> = _submissionState

    fun parse(raw: String){
        viewModelScope.launch {
            val result = repo.parse(raw, LocalDate.now()).getOrNull()
            _parsed.value = result
        }
    }

    fun submit() {
        val result = parsed.value ?: return

        viewModelScope.launch {
            _submissionState.value = SubmissionState.Loading
            try {
                repo.submit(result)
                _submissionState.value = SubmissionState.Success
            } catch (e: Exception) {
                _submissionState.value = SubmissionState.Error
            }
        }
    }
}
