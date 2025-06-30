package com.example.jetpackdemo.presentation.auth.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.Utility

@Composable
fun LoginScreen(authViewModel: AuthViewModel = hiltViewModel(), onNavigateToSignup: () -> Unit) {
    val context = LocalContext.current
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.welcome_txt),
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                    )
                    IconButton(onClick = { /* Handle 3-dot click */ }) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.Menu,
                            tint = Color.White,
                            contentDescription = "More options"
                        )
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)) {
                        CustomButton(
                            text = stringResource(R.string.login),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                Utility.showToast(context, "Hello")
                            })
                    }

                }


            }
        }


    }
}