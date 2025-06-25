package com.sapreme.dailyrank.ui.util.validation

sealed class ValidationResult {
    data object Valid: ValidationResult()
    data class Invalid(val error: String): ValidationResult()
}