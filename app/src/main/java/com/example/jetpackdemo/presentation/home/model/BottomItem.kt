package com.example.jetpackdemo.presentation.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.jetpackdemo.utils.Routes

data class BottomItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)
val bottomItems = listOf(
    BottomItem(Routes.TAB_HOME_SCREEN,    Icons.Default.Home,    "Home"),
    BottomItem(Routes.TAB_SEARCH_SCREEN,  Icons.Default.Search,  "Search"),
    BottomItem(Routes.TAB_PRODUCTS_SCREEN, Icons.Default.Favorite,  "Products"),
    BottomItem(Routes.TAB_SETTING_SCREEN,Icons.Default.Settings,"Settings")
)