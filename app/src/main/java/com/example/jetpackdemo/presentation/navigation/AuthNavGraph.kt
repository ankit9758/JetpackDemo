package com.example.jetpackdemo.presentation.navigation


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.jetpackdemo.presentation.auth.screens.ChangePasswordScreen
import com.example.jetpackdemo.presentation.auth.screens.ForgotPasswordScreen
import com.example.jetpackdemo.presentation.auth.screens.LoginScreen
import com.example.jetpackdemo.presentation.auth.screens.RegisterScreen
import com.example.jetpackdemo.presentation.auth.screens.VerifyEmailByOtpScreen
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.utils.Routes


/* ---------- AUTH GRAPH ---------- */

fun NavGraphBuilder.authGraph(navController: NavController) {
        composable(route = Routes.SPLASH) {
            val authViewModel: AuthViewModel = hiltViewModel()
            SplashScreen(navController, authViewModel, onNavigateToHome = {
                navController.navigate(route = Routes.HOME) {
                    popUpTo(0)    // ⬅️ clears ALL previous destinations
                    launchSingleTop = true
                }
            },
                onNavigateToLogin = {
                    navController.navigate(route = Routes.LOGIN) {
                    popUpTo(0)    // ⬅️ clears ALL previous destinations
                    launchSingleTop = true
                }})
        }
        composable(route = Routes.LOGIN) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignup = { navController.navigate(route = Routes.REGISTER) },
                onNavigateToForgotPassword = { navController.navigate(route = Routes.FORGOT_PASSWORD) },
                onNavigateToHomeScreen = { email ->
                    navController.navigate(route = "home_screen/$email") {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                }

            )
        }
        composable(route = Routes.REGISTER) {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navController.navigateUp() },
                onNavigateToHomeScreen = { email ->
                    navController.navigate(route = "home_screen/$email") {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Routes.FORGOT_PASSWORD) {
            val authViewModel: AuthViewModel = hiltViewModel()
            ForgotPasswordScreen(
                authViewModel = authViewModel,
                onNavigateToEmailVerification = { email -> navController.navigate(route = "VerifyEmailByOtp/$email") },
                onBackButtonClick = { navController.popBackStack() })
        }
        composable(route = "VerifyEmailByOtp/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val authViewModel: AuthViewModel = hiltViewModel()
            VerifyEmailByOtpScreen(
                authViewModel = authViewModel,
                onBackButtonClick = { navController.popBackStack() },
                onNavigateToChangePassword = { navController.navigate(route = "ChangePassword/$email") },
                email = email
            )
        }
        composable(route = "ChangePassword/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val authViewModel: AuthViewModel = hiltViewModel()
            ChangePasswordScreen(
                authViewModel = authViewModel,
                onBackButtonClick = { navController.popBackStack() },
                onChangePasswordUseCase = {
                    navController.navigate(route = Routes.LOGIN) {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                },
                email = email
            )
        }
}