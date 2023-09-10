package com.aj.ajnews.domain.repositiry

import com.aj.ajnews.data.remote.dto.ArticlesResponse

interface NewsRepository {

    suspend fun getTopHeadLines(country: String, page: Int): ArticlesResponse

    suspend fun getTopPicks(country: String, category: String, count: Int): ArticlesResponse

    suspend fun searchNews(searchQuery: String, page: Int): ArticlesResponse

}