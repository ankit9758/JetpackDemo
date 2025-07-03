package com.example.jetpackdemo.presentation.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme

@Composable
fun HomeScreen(authViewModel: AuthViewModel = hiltViewModel(),name: String) {
    JetpackDemoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2196F3),
                            // Blue
                            Color(0xFF21CBF3)
                        ),
                    )
                ),
        ) {
         Text(text = name,
             style = MaterialTheme.typography.displayMedium,
             color = Color.White)
        }
    }

}