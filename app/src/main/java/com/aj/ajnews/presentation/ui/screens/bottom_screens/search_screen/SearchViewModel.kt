package com.aj.ajnews.presentation.ui.screens.bottom_screens.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.domain.use_case.SearchArticlesUseCase
import com.aj.ajnews.presentation.ui.MainActivity
import com.aj.ajnews.presentation.ui.MainActivity.Companion.showWebView
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchArticlesUseCase: SearchArticlesUseCase,
    val localRepository: SavedArticlesRepository
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())
        private set

    var isArticleSaved by mutableStateOf(false)
        private set

    var currentUrl by mutableStateOf("")
        private set

    private var currentIndex: Int = 0
    private lateinit var articleInfo: SavedArticlesEntity

    private val networkObserver = Observer<Boolean> { isAvailable ->
        if (isAvailable) loadArticles()
    }

    init {
        MainActivity.isNetworkAvailable.observeForever(networkObserver)
    }

    override fun onCleared() {
        super.onCleared()
        MainActivity.isNetworkAvailable.removeObserver(networkObserver)
    }

    fun onEvent(event: SearchScreenEvent) {
        when(event) {
            is SearchScreenEvent.OnSearchTextChanged -> {
                state = state.copy(searchText = event.value, page = 1, searchResult = listOf())
                loadArticles()
            }

            is SearchScreenEvent.OnSaveArticleClicked -> viewModelScope.launch {
                val article = state.searchResult[event.index]
                val articleInfo = SavedArticlesEntity(
                    title = article.title,
                    description = article.description,
                    imageUrl = article.imageUrl,
                    url = article.url
                )

                if (article.isSaved.value) {
                    localRepository.deleteArticle(article = articleInfo)
                    state.searchResult[event.index].isSaved.value = false
                } else {
                    localRepository.upsertArticle(article = articleInfo)
                    state.searchResult[event.index].isSaved.value = true
                }
            }

            is SearchScreenEvent.OnReadMoreClicked -> {
                val article = state.searchResult[event.index]
                currentIndex = event.index
                initiateWebView(article)
            }

            is SearchScreenEvent.OnSaveArticledClickedInWebView -> viewModelScope.launch {
                if (state.searchResult[currentIndex].isSaved.value) {
                    localRepository.deleteArticle(article = articleInfo)
                    state.searchResult[currentIndex].isSaved.value = false
                    isArticleSaved = false
                } else {
                    localRepository.upsertArticle(article = articleInfo)
                    state.searchResult[currentIndex].isSaved.value = true
                    isArticleSaved = true
                }
            }

            is SearchScreenEvent.OnBackPressedOnWebView -> showWebView = false
        }
    }

    fun loadArticles() {
        searchArticlesUseCase(query = state.searchText, page = state.page).onEach { result ->
            state = when(result) {
                is Result.Loading -> {
                    if (state.page == 1) {
                        state.copy(isInitialLoadingInProgress = true)
                    } else {
                        state.copy(isPaginating = true)
                    }
                }

                is Result.Success -> state.copy(searchResult = if (state.searchText.isNotBlank()) state.searchResult + result.data!! else listOf(), isInitialLoadingInProgress = false, isPaginating = false, page = state.page + 1, isEndReached = result.data?.isEmpty()!!, error = "")
                is Result.Failure -> state.copy(error = if (state.searchText.isNotBlank()) result.error!! else "", isInitialLoadingInProgress = false, isPaginating = false)
            }
        }.launchIn(viewModelScope)
    }

    private fun initiateWebView(article: ArticleInfo) {
        currentUrl = article.url
        isArticleSaved = article.isSaved.value
        showWebView = true
        articleInfo = SavedArticlesEntity(
            title = article.title,
            description = article.description,
            imageUrl = article.imageUrl,
            url = article.url
        )
    }

}