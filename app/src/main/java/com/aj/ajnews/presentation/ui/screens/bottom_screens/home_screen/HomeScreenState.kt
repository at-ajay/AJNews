package com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen

import com.aj.ajnews.util.ArticleInfo

data class HomeScreenState(
    val trendingArticles: List<ArticleInfo> = listOf(),
    val topPicksArticles: List<ArticleInfo> = listOf(),
    val error: String = "",
    val isInitialLoadingInProgress: Boolean = false,
    val page: Int = 1,
    val isEndReached: Boolean = false,
    val isPaginating: Boolean = false
)
