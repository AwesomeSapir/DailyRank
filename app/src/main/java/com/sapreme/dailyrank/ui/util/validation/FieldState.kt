package com.sapreme.dailyrank.ui.util.validation

data class FieldState(
    val text: String = "",
    val validation: ValidationResult = ValidationResult.Valid,
)
