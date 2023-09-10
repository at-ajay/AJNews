package com.aj.ajnews.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.aj.ajnews.R

sealed class BottomNavScreen(
    val route : String,
    val label : Int,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
) {
    object HomeScreen : BottomNavScreen("home_screen", R.string.home, Icons.Rounded.Home, Icons.Outlined.Home)
    object SavedArticlesScreen : BottomNavScreen("saved_articles_screen", R.string.saved, Icons.Rounded.Bookmark, Icons.Outlined.BookmarkBorder)
    object SettingsScreen : BottomNavScreen("settings_screen", R.string.settings, Icons.Rounded.Settings, Icons.Outlined.Settings)
    object SearchScreen: BottomNavScreen("search_screen", R.string.search, Icons.Rounded.Search, Icons.Outlined.Search)
}
