package com.example.jetpackdemo.presentation.auth.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.jetpackdemo.presentation.auth.state.AuthUiState
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    private val mockViewModel = mockk<AuthViewModel>(relaxed = true)
    private val fakeUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)

    @Before
    fun setup() {
        every { mockViewModel.uiState } returns fakeUiState
    }


    @Test
    fun loginScreen_displaysEmailAndPasswordFields() {
        composeTestRule.setContent {
            LoginScreen(
                authViewModel = mockViewModel,
                onNavigateToSignup = {},
                onNavigateToForgotPassword = {},
                onNavigateToHomeScreen = {}
            )
        }

        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }

}