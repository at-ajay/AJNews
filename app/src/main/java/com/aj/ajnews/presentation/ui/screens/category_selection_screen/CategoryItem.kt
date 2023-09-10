package com.aj.ajnews.presentation.ui.screens.category_selection_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class CategoryItem(
    val label: String,
    val apiName: String,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
