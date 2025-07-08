package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.state.AuthUiState
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.Utility

@Composable
fun RegisterScreen(authViewModel: AuthViewModel = hiltViewModel(),onNavigateBack: () -> Unit,
                   onNavigateToHomeScreen: (String) -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    /** <- NEW: flag that drives the progress bar */
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        authViewModel.uiState.collect { state ->
            when (state) {
                is AuthUiState.Success -> {
                    isLoading = false
                    onNavigateToHomeScreen(email)
                }

                is AuthUiState.Error -> {
                    isLoading = false
                    Utility.showToast(context, (state).message)
                }

                is AuthUiState.ErrorWithId -> {
                    isLoading = false
                    Utility.showToast(context, context.getString(state.id))
                }

                is AuthUiState.Loading -> {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.welcome_txt_register),
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
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .imePadding() // important!
                            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                    ) {
                        OutLineEditText(
                            value = name,
                            onValueChange = { it -> name = it.filter { it.isLetter() }},
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.name),
                            placeHolder = stringResource(R.string.enter_name),
                            keyboardType = KeyboardType.Ascii,
                            startIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Name Icon", tint = Color.Gray) }

                        )

                        OutLineEditText(
                            value = email,
                            onValueChange = { email = it.filter { char ->
                                char.isLetterOrDigit() || char in listOf('@', '.', '_', '-')
                            } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.email),
                            placeHolder = stringResource(R.string.enter_email),
                            keyboardType = KeyboardType.Email,
                            startIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon", tint = Color.Gray) }

                        )
                        OutLineEditText(
                            value = mobileNumber,
                            onValueChange = { it -> mobileNumber = it.filter { it.isDigit() } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.mobile_no),
                            placeHolder = stringResource(R.string.enter_phone),
                            keyboardType = KeyboardType.Number,
                            maxLength=10,
                            startIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = "Mobile Icon", tint = Color.Gray) }

                        )

                        OutLineEditText(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.password),
                            placeHolder = stringResource(R.string.enter_password),
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password,
                            isPassword = !passwordVisible,
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Info Icon"
                                    )

                                }
                            },
                            startIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Setting Icon", tint = Color.Gray) }
                        )
                        OutLineEditText(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 20.dp),
                            label = stringResource(R.string.confirm_password),
                            placeHolder = stringResource(R.string.enter_confirm_password),
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password,
                            isPassword = !confirmPasswordVisible,
                            trailingIcon = {
                                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Info Icon"
                                    )

                                }
                            },
                            startIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Setting Icon", tint = Color.Gray) }
                        )


                        CustomButton(
                            text = stringResource(R.string.register),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                               authViewModel.onRegister(email,password,name,mobileNumber,confirmPassword)
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
                                text = stringResource(id = R.string.login),
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = TextDecoration.Underline,
                                color = Color(0xFF1E9AC4),
                                modifier = Modifier.clickable { onNavigateBack() }

                                )
                        }
                    }

                }


            }

        }

        LoadingOverlay(isLoading)
    }
}