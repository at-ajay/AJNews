package com.aj.ajnews.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.aj.ajnews.presentation.navigation.BottomNavigation
import com.aj.ajnews.presentation.ui.MainActivity.Companion.showWebView
import com.aj.ajnews.presentation.ui.components.BottomNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen() {

    val btmNavController = rememberNavController()
    Scaffold(
        bottomBar = { if (!showWebView) BottomNavigationBar(navController = btmNavController) }
    ) {
        BottomNavigation(btmNavController = btmNavController)
    }
    
}

@Preview(showSystemUi = true)
@Composable
fun HostScreenPreview() {
    HostScreen()
}