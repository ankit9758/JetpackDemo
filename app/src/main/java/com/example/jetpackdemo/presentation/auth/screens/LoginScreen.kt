package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.Utility

@Composable
fun LoginScreen(authViewModel: AuthViewModel = hiltViewModel(), onNavigateToSignup: () -> Unit) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                            modifier = Modifier
                                .size(40.dp)
                                .rotate(90f),
                            imageVector = Icons.Default.MoreVert,
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 80.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                    ) {
                        OutLineEditText(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.email),
                            placeHolder = stringResource(R.string.enter_email),
                            keyboardType = KeyboardType.Email

                        )

                        OutLineEditText(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.password),
                            placeHolder = stringResource(R.string.enter_password),
                            keyboardType = KeyboardType.Password,
                            isPassword = !passwordVisible
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp, bottom = 30.dp),
                            text = stringResource(id = R.string.forgot_password),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF52BFE3),
                            textAlign = TextAlign.Right
                        )
                        CustomButton(
                            text = stringResource(R.string.login),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                Utility.showToast(context, "Hello")
                            })
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 30.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.do_not_account),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF52BFE3),
                            )
                            Text(
                                text = stringResource(id = R.string.register),
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = TextDecoration.Underline,
                                color = Color(0xFF1E9AC4),

                            )
                        }
                    }

                }


            }
        }


    }
}