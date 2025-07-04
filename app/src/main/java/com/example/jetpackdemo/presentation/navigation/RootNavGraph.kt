package com.example.jetpackdemo.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.jetpackdemo.utils.Routes

/* ---------- ROOT ---------- */

@Composable
fun RootNavGraph() {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = Routes.AUTH_GRAPH,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(350)) },
        exitTransition  = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,  tween(350)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right,tween(350)) },
        popExitTransition  = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right,tween(350)) }
    ) {
        navigation(startDestination = Routes.SPLASH, route = Routes.AUTH_GRAPH,)   { authGraph(rootNavController) }
        navigation(startDestination = Routes.HOME.replace("{email}", "Ankit"), route = Routes.HOME_GRAPH){ homeGraph(rootNavController) }
    }
}
