package com.example.jetpackdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun JetpackDemoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = AppTypography,   // ← custom fonts injected here
        content = content
    )
}