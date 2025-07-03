package com.example.jetpackdemo.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarController(
    private val scope: CoroutineScope,
    private val hostState: SnackbarHostState
) {
    fun show(message: String) {
        scope.launch {
            val result = hostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }
}