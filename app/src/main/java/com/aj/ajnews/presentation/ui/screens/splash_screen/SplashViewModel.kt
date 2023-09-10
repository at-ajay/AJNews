package com.aj.ajnews.presentation.ui.screens.splash_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.local.datastore.DataStore
import com.aj.ajnews.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStore: DataStore): ViewModel() {

    var isBrandNameLaunched by mutableStateOf(false)
    var isAppNameLaunched by mutableStateOf(false)
    var nextScreenRoute by mutableStateOf(Screen.CategorySelectionScreen.route)

    init {
        viewModelScope.launch {
            val data = dataStore.getUserInterest()
            data?.let {
                nextScreenRoute = Screen.HostScreen.route
            }
        }
    }

}