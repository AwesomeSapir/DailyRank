package com.sapreme.dailyrank.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapreme.dailyrank.data.remote.firebase.FirebaseAuthManager
import com.sapreme.dailyrank.data.repository.GroupRepository
import com.sapreme.dailyrank.util.validation.NotEmptyRule
import com.sapreme.dailyrank.util.validation.RangeLengthRule
import com.sapreme.dailyrank.util.validation.ValidatedField
import com.sapreme.dailyrank.util.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val auth: FirebaseAuthManager,
    private val groupRepo: GroupRepository,
) : ViewModel() {

    val groupNameField = ValidatedField(
        listOf(
            NotEmptyRule(),
            RangeLengthRule(3, 50)
        )
    )

    fun onGroupNameChanged(input: String) {
        groupNameField.onTextChanged(input)
    }

    fun createGroup(onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        if (groupNameField.state.value.validation is ValidationResult.Valid) {
            viewModelScope.launch {
                try {
                    val uid = auth.uid() ?: return@launch
                    groupRepo.createGroup(
                        name = groupNameField.state.value.text.trim(),
                        creatorId = uid
                    )
                    onSuccess()
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }


}