package com.sapreme.dailyrank.data.auth

sealed interface AuthStatus {
    data class Authenticated(val uid: String) : AuthStatus
    data object Unauthenticated : AuthStatus
}