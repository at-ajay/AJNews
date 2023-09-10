package com.aj.ajnews.presentation.ui.screens.category_selection_screen

sealed class CategorySelectionUIEvent {
    object OnCategorySavingProcessed: CategorySelectionUIEvent()
}