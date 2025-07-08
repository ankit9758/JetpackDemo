package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.state.AuthUiState
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.ui.theme.Montserrat
import com.example.jetpackdemo.utils.BackToolbar
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.Utility

@Composable
fun ForgotPasswordScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToEmailVerification: (String) -> Unit, onBackButtonClick: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    /** <- NEW: flag that drives the progress bar */
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.uiState.collect { state ->
            when (state) {
                is AuthUiState.Success -> {
                    isLoading = false
                    onNavigateToEmailVerification(email)
                }

                is AuthUiState.Error -> {
                    isLoading = false
                    Utility.showToast(context, (state).message)


                }

                is AuthUiState.ErrorWithId -> {
                    isLoading = false
                    Utility.showToast(context, context.getString(state.id))
                }

                AuthUiState.Loading -> {
                    isLoading = true
                }

                else -> {

                }
            }
        }
    }


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
                BackToolbar(
                    title = stringResource(id = R.string.forgot_password),
                    onBackClick = { onBackButtonClick() }
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp, start = 25.dp, end = 25.dp, bottom = 30.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.mail_address),
                            style = TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = Color(0xFF2196F3), modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.associate_email),
                            style = TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Thin,
                                fontSize = 18.sp
                            ),
                            color = Color.Gray, modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            textAlign = TextAlign.Center
                        )

                        OutLineEditText(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.email),
                            placeHolder = stringResource(R.string.enter_email),
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email Icon",
                                    tint = Color.Gray
                                )
                            }

                        )
                        Spacer(modifier = Modifier.size(40.dp))

                        CustomButton(
                            text = stringResource(R.string.recover_password),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                authViewModel.onRecoverPassword(email)
                            })


                    }

                }

            }
            LoadingOverlay(isLoading)
        }


    }
}