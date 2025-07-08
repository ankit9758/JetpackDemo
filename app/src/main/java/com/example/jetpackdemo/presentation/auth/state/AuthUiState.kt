package com.example.jetpackdemo.presentation.auth.state


sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    // 👇 Generic wrapper for a result (like a user)
    data class Result<T>(val data: T?) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    data class ErrorWithId(val id: Int) : AuthUiState()
}