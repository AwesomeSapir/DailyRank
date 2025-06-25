package com.sapreme.dailyrank.data.auth

interface AuthResult {
    data class Success(val uid: String) : AuthResult
    data class Error(val message: String) : AuthResult
    data object Loading : AuthResult
}