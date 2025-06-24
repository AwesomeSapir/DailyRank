package com.sapreme.dailyrank.util.validation

interface ValidationRule {
    fun validate(input: String): ValidationResult
}