package com.sapreme.dailyrank.util.validation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class ValidatedField(
    private val rules: List<ValidationRule>,
) {
    private val _state = mutableStateOf(FieldState())
    val state: State<FieldState> = _state

    fun onTextChanged(input: String){
        val firstInvalid = rules.map { it.validate(input) }.firstOrNull{ it is ValidationResult.Invalid }

        val validation = firstInvalid ?: ValidationResult.Valid
        _state.value = FieldState(input, validation)
    }

}