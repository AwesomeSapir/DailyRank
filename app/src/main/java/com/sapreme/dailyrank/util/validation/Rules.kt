package com.sapreme.dailyrank.util.validation

class NotEmptyRule(val errorMsg: String = "Required") : ValidationRule {
    override fun validate(input: String) =
        if (input.isBlank()) ValidationResult.Invalid(errorMsg)
        else ValidationResult.Valid
}

class RangeLengthRule(
    val min: Int,
    val max: Int,
    val errorMsg: String = "Length must be between $min and $max"
) :
    ValidationRule {
    override fun validate(input: String) =
        if (input.length !in min..max) ValidationResult.Invalid(errorMsg)
        else ValidationResult.Valid
}

class MinLengthRule(val min: Int, val errorMsg: String = "Length must be at least $min") :
    ValidationRule {
    override fun validate(input: String) =
        if (input.length < min) ValidationResult.Invalid(errorMsg)
        else ValidationResult.Valid
}

class MaxLengthRule(val max: Int, val errorMsg: String = "Length must be at most $max") :
    ValidationRule {
    override fun validate(input: String) =
        if (input.length > max) ValidationResult.Invalid(errorMsg)
        else ValidationResult.Valid
}

class RegexRule(val pattern: Regex, val errorMsg: String = "Invalid format") : ValidationRule {
    override fun validate(input: String) =
        if (!pattern.matches(input)) ValidationResult.Invalid(errorMsg)
        else ValidationResult.Valid
}