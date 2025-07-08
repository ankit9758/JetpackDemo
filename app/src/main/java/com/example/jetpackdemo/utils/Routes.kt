package com.example.jetpackdemo.utils

object Routes {
    const val AUTH_GRAPH = "auth"
    const val HOME_GRAPH = "home"

    // Auth‑graph destinations
    const val LOGIN   = "login"
    const val SPLASH  = "splash"
    const val REGISTER  = "register"
    const val FORGOT_PASSWORD  = "ForgotPassword"
    const val TAB_HOME_SCREEN = "tab_home_screen"
    const val TAB_SEARCH_SCREEN = "tab_search_screen"
    const val TAB_SETTING_SCREEN = "tab_setting_screen"
    const val TAB_PRODUCTS_SCREEN = "tab_products_screen"

    // Home‑graph destinations
    const val HOME    = "home_screen/{email}"
    const val EDIT_PROFILE    = "edit_profile"
}