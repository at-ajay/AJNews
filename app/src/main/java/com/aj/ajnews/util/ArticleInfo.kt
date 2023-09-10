package com.aj.ajnews.util

import androidx.compose.runtime.MutableState

data class ArticleInfo(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val url: String,
    var isSaved: MutableState<Boolean>
)
