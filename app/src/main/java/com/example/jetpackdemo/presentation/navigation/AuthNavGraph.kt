package com.example.jetpackdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.presentation.auth.screens.LoginScreen
import com.example.jetpackdemo.presentation.auth.screens.RegisterScreen
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val authViewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignup = { navController.navigate("register") })
        }
        composable("register") {
            val authViewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateBack = { navController.navigateUp() })
        }
    }

}