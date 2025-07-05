package com.example.jetpackdemo.presentation.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.BackToolbar
import com.example.jetpackdemo.utils.CustomButton
import com.example.jetpackdemo.utils.LoadingOverlay
import com.example.jetpackdemo.utils.OutLineEditText
import com.example.jetpackdemo.utils.UserPreferences

@Composable
fun EditProfileScreen(onBackButtonClick: () -> Unit){
    val ctx = LocalContext.current
    val profileFlow = remember { UserPreferences(ctx).getProfile() }
    // collect → Compose State
    val profile by profileFlow.collectAsState(initial = null)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isEditable by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        profile?.let {
            name = it.username
            email = it.email
            mobileNumber = it.phoneNumber
            password = it.password
        }
    }

    /** <- NEW: flag that drives the progress bar */
    var isLoading by remember { mutableStateOf(false) }


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
                    title = stringResource(id = R.string.edit_profile),
                    onBackClick = { onBackButtonClick() }
                )
              /*  Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.BottomEnd
                ){
                    // Profile image (in circle)
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    // Small edit button
                    IconButton(
                        onClick = {  },
                        modifier = Modifier.padding(4.dp)
                            .size(50.dp)
                           // .offset(x = 4.dp, y = 4.dp) // fine-tune position
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .border(1.dp, Color.LightGray, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }*/

                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally)) {
                    Card(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.size(120.dp),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.avatar),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    IconButton(
                        onClick = {  },
                        modifier = Modifier
                            .offset(x = (-2).dp, y = (-2).dp)
                            .size(24.dp)
                            .background(color = Color(0xFF4285F4), shape = CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
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
                            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 40.dp)
                    ) {

                        OutLineEditText(
                            value = name,
                            onValueChange = { it -> name = it.filter { it.isLetter() } },
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.name),
                            placeHolder = stringResource(R.string.enter_name),
                            isEditable = isEditable,
                            keyboardType = KeyboardType.Ascii,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Name Icon",
                                    tint = Color.Gray
                                )
                            }

                        )

                        OutLineEditText(
                            value = email,
                            onValueChange = {
                                email = it.filter { char ->
                                    char.isLetterOrDigit() || char in listOf('@', '.', '_', '-')
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.email),
                            placeHolder = stringResource(R.string.enter_email),
                            isEditable = false,
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
                            value = mobileNumber,
                            onValueChange = { it -> mobileNumber = it.filter { it.isDigit() } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            label = stringResource(R.string.mobile_no),
                            placeHolder = stringResource(R.string.enter_phone),
                            keyboardType = KeyboardType.Number,
                            maxLength = 10,
                            isEditable = isEditable,
                            imeAction = ImeAction.Done,
                            startIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = "Mobile Icon",
                                    tint = Color.Gray
                                )
                            }

                        )

                        OutLineEditText(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            label = stringResource(R.string.password),
                            placeHolder = stringResource(R.string.enter_password),
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password,
                            isEditable = false,
                            isPassword = !passwordVisible,
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Info Icon"
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

                        CustomButton(
                            text = stringResource(R.string.update_profile),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                isEditable =!isEditable
                            })
                    }
                }
            }

            LoadingOverlay(isLoading)
        }
    }

}