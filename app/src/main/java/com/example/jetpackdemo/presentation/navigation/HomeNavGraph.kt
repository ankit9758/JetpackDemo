package com.example.jetpackdemo.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.presentation.home.screens.MainHomeScreen
import com.example.jetpackdemo.presentation.profile.screens.EditProfileScreen
import com.example.jetpackdemo.presentation.profile.viewmodels.ProfileViewModel
import com.example.jetpackdemo.utils.Routes

//---------- HOME GRAPH ----------

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable(Routes.HOME, arguments = listOf(navArgument("email") { defaultValue = "" })) {backStackEntry->
        val name = backStackEntry.arguments?.getString("email") ?: ""
        MainHomeScreen(name, onLogoutNavigate = {
            navController.navigate(Routes.LOGIN){
                popUpTo(Routes.HOME_GRAPH) { inclusive = true }
            }
        },onEditProfileClick = {
            navController.navigate(route=Routes.EDIT_PROFILE)
        }, onChangePasswordClick = {
            navController.navigate("ChangePassword/$it/${true}"){
            }
        })
    }
    composable(route = Routes.EDIT_PROFILE) { backStackEntry ->
        val profileViewModel: ProfileViewModel = hiltViewModel()
        EditProfileScreen(profileViewModel,onBackButtonClick = { navController.popBackStack() })
    }
}
