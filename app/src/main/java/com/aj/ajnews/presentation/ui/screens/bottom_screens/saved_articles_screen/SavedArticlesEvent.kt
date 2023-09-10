package com.aj.ajnews.presentation.ui.screens.bottom_screens.saved_articles_screen

import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity

sealed class SavedArticlesEvent() {
    object OnDeleteAllClicked: SavedArticlesEvent()
    data class OnDeleteArticleClicked(val article: SavedArticlesEntity): SavedArticlesEvent()
    data class OnReadMoreClicked(val url: String): SavedArticlesEvent()
    object OnBackPressedOnWebView: SavedArticlesEvent()
}
