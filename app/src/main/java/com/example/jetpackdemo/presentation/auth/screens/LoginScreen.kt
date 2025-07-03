package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.AuthUiState
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.SnackBarController

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToSignup: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,onNavigateToHomeScreen: (String) -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarController = remember { SnackBarController(scope, snackBarHostState) }
    /** <- NEW: flag that drives the progress bar */
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.uiState.collect { state->
            when (state) {
                is AuthUiState.Success -> {
                    isLoading=false
                    onNavigateToHomeScreen("ankit")
                }

                is AuthUiState.Error -> {
                    isLoading=false
                  //  Utility.showToast(context, (state).message)
                     snackBarController.show(state.message)

                }

                is AuthUiState.ErrorWithId -> {
                    isLoading=false
                      snackBarController.show(context.getString(state.id))
                  //  Utility.showToast(context, context.getString(state.id))
                }
                is AuthUiState.Loading->{
                    isLoading=true
                }

                else -> {

                }
            }
        }
    }


    JetpackDemoTheme {
        Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
            containerColor = Color.Transparent // Make Scaffo
            ) { scaffoldPadding->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding) // Apply
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
                                .verticalScroll(scrollState)
                                .imePadding() // important!
                                .padding(top = 80.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                        ) {
                            OutLineEditText(
                                value = email,
                                onValueChange = { email = it.filter { char ->
                                    char.isLetterOrDigit() || char in listOf('@', '.', '_', '-')
                                } },
                                modifier = Modifier.fillMaxWidth(),
                                label = stringResource(R.string.email),
                                placeHolder = stringResource(R.string.enter_email),
                                keyboardType = KeyboardType.Email,
                                startIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Email Icon",
                                        tint = Color.Gray
                                    )
                                }

                            )

                            OutLineEditText(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                label = stringResource(R.string.password),
                                placeHolder = stringResource(R.string.enter_password),
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password,
                                isPassword = !passwordVisible,
                                trailingIcon = {
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "Setting Icon"
                                        )

                                    }
                                },
                                startIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Setting Icon",
                                        tint = Color.Gray
                                    )
                                }
                            )
                            TextButton(onClick = onNavigateToForgotPassword) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 15.dp, bottom = 30.dp),
                                    text = stringResource(id = R.string.forgot_password),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color(0xFF52BFE3),
                                    textAlign = TextAlign.Right
                                )
                            }

                            CustomButton(
                                text = stringResource(R.string.login),
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    authViewModel.onLogin(email,password)
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
                                    color = Color.Gray,
                                )
                                Text(
                                    modifier = Modifier.clickable {
                                        onNavigateToSignup()
                                    },
                                    text = stringResource(id = R.string.register),
                                    style = MaterialTheme.typography.titleMedium,
                                    textDecoration = TextDecoration.Underline,
                                    color = Color(0xFF1E9AC4),

                                    )
                            }
                        }

                    }

                }
                LoadingOverlay(isLoading)
            }
        }



    }
}