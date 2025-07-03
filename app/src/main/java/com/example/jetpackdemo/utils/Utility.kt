package com.example.jetpackdemo.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Utility {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(
        scope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        message: String,
        actionLabel: String? = null
    ) {
        scope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            )
        }
    }
}