package com.aj.ajnews.presentation.navigation

sealed class Screen(val route : String) {
    object SplashScreen: Screen("splash_screen")
    object CategorySelectionScreen: Screen("category_selection_screen")
    object HostScreen: Screen("host_screen")
}
