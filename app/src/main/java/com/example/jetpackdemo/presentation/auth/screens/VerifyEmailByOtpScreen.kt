package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.R
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.ui.theme.Montserrat
import com.example.jetpackdemo.utils.BackToolbar
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.OtpScreen
import com.example.jetpackdemo.utils.Utility

@Composable
fun VerifyEmailByOtpScreen(authViewModel: AuthViewModel = hiltViewModel(),
                           onBackButtonClick: () -> Unit) {
    val context = LocalContext.current
    var otp by remember { mutableStateOf("") }

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
                    title = stringResource(id = R.string.email_verification),
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
                            text = stringResource(id = R.string.get_code),
                            style = TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            ),
                            color = Color(0xFF2196F3), modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.four_digit_code),
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
                        OtpScreen { enteredOtp ->
                            otp = enteredOtp
                            Utility.showToast(context,"OTP: $enteredOtp")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.do_not_receive_code),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray,
                            )
                            Text(
                                modifier = Modifier.clickable{
                                    Utility.showToast(context,context.getString(R.string.code_sent))
                                },
                                text = stringResource(id = R.string.resend),
                                style = MaterialTheme.typography.titleMedium,
                                textDecoration = TextDecoration.Underline,
                                color = Color(0xFF1E9AC4),

                                )

                        }
                        Spacer(modifier = Modifier.size(20.dp))

                        CustomButton(
                            text = stringResource(R.string.verify_proceed),
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