package com.aj.ajnews.presentation.ui.screens.bottom_screens.search_screen

sealed class SearchScreenEvent {
    data class OnSearchTextChanged(val value: String): SearchScreenEvent()
    data class OnSaveArticleClicked(val index: Int): SearchScreenEvent()
    data class OnReadMoreClicked(val index: Int): SearchScreenEvent()
    object OnSaveArticledClickedInWebView: SearchScreenEvent()
    object OnBackPressedOnWebView: SearchScreenEvent()
}
