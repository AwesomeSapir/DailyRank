package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.GroupRepository
import com.sapreme.dailyrank.ui.util.validation.MaxLengthRule
import com.sapreme.dailyrank.ui.util.validation.NotEmptyRule
import com.sapreme.dailyrank.ui.util.validation.RangeLengthRule
import com.sapreme.dailyrank.ui.util.validation.ValidatedField
import com.sapreme.dailyrank.ui.util.validation.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateGroupViewModel(
    private val auth: FirebaseAuthManager,
    private val groupRepo: GroupRepository,
) :ViewModel() {

    val groupNameField = ValidatedField(listOf(
        NotEmptyRule(),
        RangeLengthRule(3, 50)
    ))

    private val _creating = MutableStateFlow(false)
    val creating: StateFlow<Boolean> = _creating.asStateFlow()

    fun onGroupCreate() {
        val state = groupNameField.state.value
        if (state.validation is ValidationResult.Invalid) return

        viewModelScope.launch {
            _creating.value = true

        }
    }



}