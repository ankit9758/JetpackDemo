package com.example.jetpackdemo.presentation.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController:NavController){
    val isLoading = remember { mutableStateOf(true) }

    // Delay navigation using side-effect
    LaunchedEffect(Unit) {
        delay(1000) // 3 seconds delay
        isLoading.value = false
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }


    // UI Part
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush  = Brush.linearGradient(
                colors = listOf(Color(0xFF2196F3), Color(0xFF21CBF3)) // Blue gradient
            )),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "App Icon",
                modifier = Modifier.size(100.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "JetPack Demo",
                color = Color.White,
               style =  MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(60.dp))
            }
        }
    }

}