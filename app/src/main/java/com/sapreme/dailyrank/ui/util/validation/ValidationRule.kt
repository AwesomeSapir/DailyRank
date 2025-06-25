package com.sapreme.dailyrank.ui.util.validation

interface ValidationRule {
    fun validate(input: String): ValidationResult
}