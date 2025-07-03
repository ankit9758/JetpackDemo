package com.example.jetpackdemo.presentation.auth.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.jetpackdemo.domain.model.User
import com.example.jetpackdemo.presentation.auth.AuthUiState
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.ui.theme.Montserrat
import com.example.jetpackdemo.utils.BackToolbar
import com.example.jetpackdemo.utils.CustomAlertDialog
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.Utility

@Composable
fun ChangePasswordScreen(authViewModel: AuthViewModel = hiltViewModel(),onBackButtonClick: () -> Unit,onChangePasswordUseCase: () -> Unit,
                         email: String){

    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    /** <- NEW: flag that drives the progress bar */
    var isLoading by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        authViewModel.findUserDetailByEmail(email)
        authViewModel.uiState.collect { state ->
            when (state) {
                is AuthUiState.Success -> {
                    isLoading = false
                    showSuccess =true
//                    Utility.showToast(context, context.getString(R.string.password_change_success))

                }

                is AuthUiState.Error -> {
                    isLoading = false
                    showError = true
                    Utility.showToast(context, (state).message)
                }

                is AuthUiState.ErrorWithId -> {
                    isLoading = false
                    showError = true
                  //  Utility.showToast(context, context.getString(state.id))
                }

                is AuthUiState.Loading -> {
                    isLoading = true
                }
                is AuthUiState.Result<*> -> {
                    isLoading = false
                   val user=state.data as? User
                    user?.let {
                       currentPassword = it.password
                    }
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
                    title = stringResource(id = R.string.reset_password),
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
                            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.enter_new_password),
                            style = TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = Color(0xFF2196F3), modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.new_password_desc),
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



                        Spacer(modifier = Modifier.size(20.dp))

                        CustomButton(
                            text = stringResource(R.string.continue_txt),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                authViewModel.onChangePassword(currentPassword=currentPassword, newPassword = password, confirmPassword = confirmPassword,email=email)
                            })


                    }

                }


            }
        }
        LoadingOverlay(isLoading)
        if (showSuccess) {
            CustomAlertDialog(
                icon = Icons.Default.CheckCircle,
                title = context.getString(R.string.reset_password),
                message = context.getString(R.string.password_change_success),
                isSuccess = true,
                onClose = {
                    showSuccess = false
                    onChangePasswordUseCase()
                },
                onConfirm = {
                    showSuccess = false
                    onChangePasswordUseCase()
                }
            )
        }
        if (showError) {
            CustomAlertDialog(
                icon = Icons.Default.Close,
                title = "Error",
                message = "Something went wrong. Please try again.",
                isSuccess = false,
                confirmText = "Retry",
                onClose = {
                    showError = false
                },
                onConfirm = {
                    showError = false
                }

            )
        }

    }

}