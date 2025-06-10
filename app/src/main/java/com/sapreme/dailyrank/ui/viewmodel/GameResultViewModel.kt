package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.repository.GameResultRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GameResultViewModel @Inject constructor(
    private val repo: GameResultRepository
) : ViewModel() {

    private val _parsed = MutableStateFlow<GameResult?>(null)
    val parsed: StateFlow<GameResult?> = _parsed

    fun parse(raw: String): GameResult = repo.submitResult(raw, LocalDate.now()).getOrThrow()

}
