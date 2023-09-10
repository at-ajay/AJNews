package com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen

sealed class HomeScreenEvent {
    data class OnReadMoreClicked(val index: Int): HomeScreenEvent()
    data class OnTopPickArticleClicked(val index: Int): HomeScreenEvent()
    data class OnSaveArticleClicked(val index: Int): HomeScreenEvent()
    object OnSaveArticledClickedInWebView: HomeScreenEvent()
    object OnBackPressedOnWebView: HomeScreenEvent()
}
