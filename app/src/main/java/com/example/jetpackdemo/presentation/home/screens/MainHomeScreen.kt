package com.example.jetpackdemo.presentation.home.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackdemo.presentation.auth.viewmodel.AuthViewModel
import com.example.jetpackdemo.presentation.home.model.bottomItems
import com.example.jetpackdemo.presentation.profile.viewmodels.ProfileViewModel
import com.example.jetpackdemo.ui.theme.JetpackDemoTheme
import com.example.jetpackdemo.utils.Routes

@Composable
fun MainHomeScreen(
    email: String,
    onLogoutNavigate: () -> Unit,onEditProfileClick: () -> Unit,onChangePasswordClick: (String) -> Unit
) {
    val nav = rememberNavController()

    val current by nav.currentBackStackEntryAsState()

    JetpackDemoTheme {
        Scaffold(
            bottomBar = {
                Box {
                    BottomAppBar(
                        containerColor = Color(0xFFEEE8E8),   // Material3 purple
                        tonalElevation = 0.dp
                    ) {
                        bottomItems.forEachIndexed { index, bottomItem ->
                            // create a void in the middle for FAB
                            if (index == 2) Spacer(Modifier.weight(.8f))
                            NavigationBarItem(
                                selected = current?.destination?.route == bottomItem.route,
                                onClick = {
                                    nav.navigate(bottomItem.route) {
                                        launchSingleTop = true
                                        popUpTo(bottomItems.first().route) { saveState = true }
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(bottomItem.icon, contentDescription = bottomItem.label)
                                },
                                label = {
                                    Text(
                                        bottomItem.label,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = if (current?.destination?.route == bottomItem.route) FontWeight.Bold else FontWeight.Light
                                    )
                                },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color(0xFF2196F3),
                                    selectedTextColor = Color(0xFF2196F3),
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = Color.Transparent // disables selected background glow
                                )
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp), // height of nav bar area + FAB overlap
                    contentAlignment = Alignment.TopCenter
                ) {
                    FloatingActionButton(
                        onClick = { /* fab action */ },
                        shape = CircleShape,
                        containerColor = Color.Yellow,
                        contentColor = Color.Black,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        modifier = Modifier
                            .offset(y = (-20).dp) // move FAB upward to overlap
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }

            },
        ) { innerPadding ->
            // Replace this with actual navigation content
            NavHost(
                navController = nav,
                startDestination = bottomItems.first().route,
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(250)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        tween(250)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(250)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        tween(250)
                    )
                }
            ) {
                composable(route = Routes.TAB_HOME_SCREEN) { HomeScreen() }
                composable(route = Routes.TAB_SEARCH_SCREEN) { SearchScreen() }
                composable(route = Routes.TAB_PROFILE_SCREEN) { ProfileScreen() }
                composable(route = Routes.TAB_SETTING_SCREEN) {
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    val authViewModel: AuthViewModel = hiltViewModel()
                    SettingsScreen( profileViewModel,email,
                        onLogoutConfirm = {
                            authViewModel.logout()
                            onLogoutNavigate()
                        },
                        onEditProfileClick = {
                           onEditProfileClick()
                        },
                        onChangePasswordClick = {email->
                            onChangePasswordClick(email)
                        }
                    )
                }
            }
        }

    }
}
