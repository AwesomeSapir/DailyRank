package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sapreme.dailyrank.data.model.FilterKey
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
    private val repo: GameResultRepository,
) : ViewModel() {

    private val _parsed = MutableStateFlow<GameResult?>(null)
    open val parsed: StateFlow<GameResult?> = _parsed

    private val _submissionState = MutableStateFlow(SubmissionState.Idle)
    val submissionState: StateFlow<SubmissionState> = _submissionState

    private val resultsMap = mutableMapOf<FilterKey, MutableStateFlow<List<GameResult>>>()

    val weeklyResultsByType: Map<GameResult.Type, StateFlow<List<GameResult>>> by lazy {
        GameResult.Type.entries.associateWith { type ->
            getResultsFor(FilterKey.Weekly(type))
        }
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun refreshAll() {
        viewModelScope.launch {
            _isRefreshing.value = true
            resultsMap.keys.toList().forEach { key ->
                val filter = key.toGameResultFilter()
                val results = repo.getUserResultsBy(
                    FirebaseAuth.getInstance().currentUser!!.uid, filter
                )
                resultsMap[key]?.value = results
            }
            _isRefreshing.value = false
        }
    }

    fun getResultsFor(key: FilterKey): StateFlow<List<GameResult>> {
        return resultsMap.getOrPut(key) {
            MutableStateFlow<List<GameResult>>(emptyList()).also { fetchInto(key, it) }
        }
    }

    private fun fetchInto(key: FilterKey, flow: MutableStateFlow<List<GameResult>>) {
        val filter = key.toGameResultFilter()
        viewModelScope.launch {
            val results =
                repo.getUserResultsBy(FirebaseAuth.getInstance().currentUser!!.uid, filter)
            flow.value = results
        }
    }

    fun parse(raw: String) {
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
