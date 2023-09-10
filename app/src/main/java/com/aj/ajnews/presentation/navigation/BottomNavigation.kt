package com.aj.ajnews.presentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen.HomeScreen
import com.aj.ajnews.presentation.ui.screens.bottom_screens.saved_articles_screen.SavedArticlesScreen
import com.aj.ajnews.presentation.ui.screens.bottom_screens.search_screen.SearchScreen
import com.aj.ajnews.presentation.ui.screens.bottom_screens.settings_screen.SettingsScreen

@Composable
fun BottomNavigation(btmNavController: NavHostController) {

    NavHost(navController = btmNavController, startDestination = BottomNavScreen.HomeScreen.route) {

        composable(
            route = BottomNavScreen.HomeScreen.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            HomeScreen()
        }

        composable(
            route = BottomNavScreen.SavedArticlesScreen.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            SavedArticlesScreen()
        }

        composable(
            route = BottomNavScreen.SettingsScreen.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            SettingsScreen()
        }

        composable(
            route = BottomNavScreen.SearchScreen.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            SearchScreen()
        }

    }

}