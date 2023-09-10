package com.aj.ajnews.data.remote.dto

data class ArticlesResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)