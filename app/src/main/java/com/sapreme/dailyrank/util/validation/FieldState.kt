package com.sapreme.dailyrank.util.validation

data class FieldState(
    val text: String = "",
    val validation: ValidationResult = ValidationResult.Valid,
)
