package com.aj.ajnews.presentation.ui.screens.bottom_screens.search_screen

import com.aj.ajnews.util.ArticleInfo

data class SearchScreenState(
    val searchText: String = "",
    val searchResult: List<ArticleInfo> = listOf(),
    val error: String = "",
    val isInitialLoadingInProgress: Boolean = false,
    val page: Int = 1,
    val isEndReached: Boolean = false,
    val isPaginating: Boolean = false
)
