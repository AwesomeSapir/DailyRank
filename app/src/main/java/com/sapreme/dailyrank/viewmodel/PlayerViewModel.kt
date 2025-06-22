package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import com.sapreme.dailyrank.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repo: PlayerRepository
): ViewModel() {

}