package com.example.jetpackdemo.presentation.profile.state


sealed class ProfileUiState {
    object Loading : ProfileUiState()
    // 👇 Generic wrapper for a result (like a user)
    data class Result<T>(val data: T?) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
    data class ErrorWithId(val id: Int) : ProfileUiState()
}