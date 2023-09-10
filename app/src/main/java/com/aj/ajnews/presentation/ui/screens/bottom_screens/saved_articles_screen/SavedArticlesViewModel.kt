package com.aj.ajnews.presentation.ui.screens.bottom_screens.saved_articles_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.presentation.ui.MainActivity.Companion.showWebView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedArticlesViewModel @Inject constructor(private val localRepository: SavedArticlesRepository): ViewModel() {

    val savedArticles = localRepository.getSavedArticles()

    var currentUrl by mutableStateOf("")
        private set

    fun onEvent(event: SavedArticlesEvent) {
        when(event) {
            is SavedArticlesEvent.OnDeleteAllClicked -> viewModelScope.launch { localRepository.deleteAll() }
            is SavedArticlesEvent.OnDeleteArticleClicked -> viewModelScope.launch { localRepository.deleteArticle(event.article) }
            is SavedArticlesEvent.OnReadMoreClicked -> {
                currentUrl = event.url
                showWebView = true
            }
            is SavedArticlesEvent.OnBackPressedOnWebView -> showWebView = false
        }
    }

}