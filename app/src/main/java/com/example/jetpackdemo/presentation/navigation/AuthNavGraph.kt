package com.example.jetpackdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.presentation.auth.screens.ForgotPasswordScreen
import com.example.jetpackdemo.presentation.auth.screens.LoginScreen
import com.example.jetpackdemo.presentation.auth.screens.RegisterScreen
import com.example.jetpackdemo.presentation.auth.screens.VerifyEmailByOtpScreen
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "splash") {
        composable(route = "splash") {
            SplashScreen(navController)
        }
        composable(route = "login") {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignup = { navController.navigate(route = "register") },
                onNavigateToForgotPassword = { navController.navigate(route = "ForgotPassword") }

            )
        }
        composable(route = "register") {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navController.navigateUp() })
        }

        composable(route = "ForgotPassword") {
            val authViewModel: AuthViewModel = hiltViewModel()
            ForgotPasswordScreen(
                authViewModel = authViewModel,
                onNavigateToEmailVerification = { email->navController.navigate(route = "VerifyEmailByOtp/$email") },
                onBackButtonClick = { navController.popBackStack() })
        }
        composable(route = "VerifyEmailByOtp/{email}") { backStackEntry->
            val email=backStackEntry.arguments?.getString("email")?:""
            val authViewModel: AuthViewModel = hiltViewModel()
            VerifyEmailByOtpScreen(authViewModel = authViewModel,
                onBackButtonClick = { navController.popBackStack() }, email = email)
        }
    }

}