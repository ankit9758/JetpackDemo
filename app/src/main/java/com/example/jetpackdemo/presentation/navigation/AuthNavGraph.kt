package com.example.jetpackdemo.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.presentation.auth.screens.ChangePasswordScreen
import com.example.jetpackdemo.presentation.auth.screens.ForgotPasswordScreen
import com.example.jetpackdemo.presentation.auth.screens.LoginScreen
import com.example.jetpackdemo.presentation.auth.screens.RegisterScreen
import com.example.jetpackdemo.presentation.auth.screens.VerifyEmailByOtpScreen
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.presentation.home.screens.HomeScreen

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController, startDestination = "splash",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(350)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(350)
            )
        }) {
        composable(route = "splash") {
            SplashScreen(navController)
        }
        composable(route = "login") {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignup = { navController.navigate(route = "register") },
                onNavigateToForgotPassword = { navController.navigate(route = "ForgotPassword") },
                onNavigateToHomeScreen = { name ->
                    navController.navigate(route = "Home/$name") {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                }

            )
        }
        composable(route = "register") {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navController.navigateUp() },
                onNavigateToHomeScreen = { name ->
                    navController.navigate(route = "Home/$name") {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = "ForgotPassword") {
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
                    navController.navigate(route = "login") {
                        popUpTo(0)    // ⬅️ clears ALL previous destinations
                        launchSingleTop = true
                    }
                },
                email = email
            )
        }
        composable(route = "Home/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val authViewModel: AuthViewModel = hiltViewModel()
            HomeScreen(authViewModel = authViewModel, name = name)
        }
    }


}