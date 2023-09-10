package com.aj.ajnews.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aj.ajnews.presentation.ui.screens.HostScreen
import com.aj.ajnews.presentation.ui.screens.splash_screen.SplashScreen
import com.aj.ajnews.presentation.ui.screens.category_selection_screen.CategorySelectionScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.CategorySelectionScreen.route) {
            CategorySelectionScreen(navController = navController)
        }

        composable(route = Screen.HostScreen.route) {
            HostScreen()
        }

    }
}