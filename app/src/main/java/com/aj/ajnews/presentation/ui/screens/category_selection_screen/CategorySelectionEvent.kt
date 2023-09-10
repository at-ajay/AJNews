package com.aj.ajnews.presentation.ui.screens.category_selection_screen

sealed class CategorySelectionEvent {
    data class OnChipSelected(val chipIndex: Int): CategorySelectionEvent()
    object OnSavePrefsClicked: CategorySelectionEvent()
}
