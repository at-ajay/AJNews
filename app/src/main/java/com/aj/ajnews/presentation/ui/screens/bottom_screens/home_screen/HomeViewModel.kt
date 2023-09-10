package com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.local.datastore.DataStore
import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.domain.use_case.GetArticlesUseCase
import com.aj.ajnews.domain.use_case.GetTopPicksUseCase
import com.aj.ajnews.presentation.ui.MainActivity.Companion.showWebView
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getTopPicksUseCase: GetTopPicksUseCase,
    private val dataStore: DataStore,
    private val localRepository: SavedArticlesRepository
): ViewModel() {

     var state by mutableStateOf(HomeScreenState())
        private set

    var isArticleSaved by mutableStateOf(false)
        private set

    var currentUrl by mutableStateOf("")
        private set
    lateinit var currentCountry: String
        private set

    private lateinit var articleInfo: SavedArticlesEntity
    private var currentIndex: Int = 0
    private lateinit var currentDataSource: String

    init {
        getTopPicks()
        loadArticles()
        viewModelScope.launch { currentCountry = dataStore.getCountryCode()!!.uppercase() }
    }

    fun refresh() {
        state = state.copy(page = 1)
        getTopPicks()
        loadArticles()
    }

    private fun getTopPicks() {
        getTopPicksUseCase().onEach { result ->
            state = when(result) {
                is Result.Loading -> state.copy(isInitialLoadingInProgress = true)
                is Result.Success -> state.copy(topPicksArticles = result.data!!, isInitialLoadingInProgress = false)
                is Result.Failure -> state.copy(error = result.error ?: "An unexpected error occurred", isInitialLoadingInProgress = false)
            }
        }.launchIn(viewModelScope)
    }

    fun loadArticles() {
        getArticlesUseCase(page = state.page).onEach { result ->
            state = when(result) {
                is Result.Loading -> {
                    if (state.page == 1) {
                        state.copy(isInitialLoadingInProgress = true)
                    } else {
                        state.copy(isPaginating = true)
                    }
                }
                is Result.Success -> state.copy(trendingArticles = state.trendingArticles + result.data!!, page = state.page + 1, isEndReached = result.data.isEmpty(), isInitialLoadingInProgress = false, isPaginating = false)
                is Result.Failure -> state.copy(error = result.error ?: "An unexpected error occurred", isInitialLoadingInProgress = false, isPaginating = false)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {

            is HomeScreenEvent.OnReadMoreClicked ->  {
                val article = state.trendingArticles[event.index]
                currentIndex = event.index
                currentDataSource = "trendingArticles"
                initiateWebView(article)
            }

            is HomeScreenEvent.OnTopPickArticleClicked -> {
                val article = state.topPicksArticles[event.index]
                currentIndex = event.index
                currentDataSource = "topPicksArticles"
                initiateWebView(article)
            }

            is HomeScreenEvent.OnSaveArticleClicked -> viewModelScope.launch {
                val article = state.trendingArticles[event.index]
                val articleInfo = SavedArticlesEntity(
                    title = article.title,
                    description = article.description,
                    imageUrl = article.imageUrl,
                    url = article.url
                )

                if (article.isSaved.value) {
                    localRepository.deleteArticle(article = articleInfo)
                    state.trendingArticles[event.index].isSaved.value = false
                } else {
                    localRepository.upsertArticle(article = articleInfo)
                    state.trendingArticles[event.index].isSaved.value = true
                }
            }

            is HomeScreenEvent.OnSaveArticledClickedInWebView -> viewModelScope.launch {
                when(currentDataSource) {
                    "topPicksArticles" -> {
                        if (state.topPicksArticles[currentIndex].isSaved.value) {
                            localRepository.deleteArticle(article = articleInfo)
                            state.topPicksArticles[currentIndex].isSaved.value = false
                            isArticleSaved = false
                        } else {
                            localRepository.upsertArticle(article = articleInfo)
                            state.topPicksArticles[currentIndex].isSaved.value = true
                            isArticleSaved = true
                        }
                    }

                    "trendingArticles" -> {
                        if (state.trendingArticles[currentIndex].isSaved.value) {
                            localRepository.deleteArticle(article = articleInfo)
                            state.trendingArticles[currentIndex].isSaved.value = false
                            isArticleSaved = false
                        } else {
                            localRepository.upsertArticle(article = articleInfo)
                            state.trendingArticles[currentIndex].isSaved.value = true
                            isArticleSaved = true
                        }
                    }
                }
            }

            is HomeScreenEvent.OnBackPressedOnWebView -> showWebView = false
        }
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