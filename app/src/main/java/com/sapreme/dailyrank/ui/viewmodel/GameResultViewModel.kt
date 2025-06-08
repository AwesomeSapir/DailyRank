package com.sapreme.dailyrank.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.sapreme.dailyrank.data.model.GameResult
import com.sapreme.dailyrank.data.repository.GameResultRepository
import java.time.LocalDate

class GameResultViewModel(
    private val repo: GameResultRepository
) : ViewModel() {
    suspend fun parse(raw: String): GameResult =
        repo.submitResult(raw, LocalDate.now()).getOrThrow()
}
