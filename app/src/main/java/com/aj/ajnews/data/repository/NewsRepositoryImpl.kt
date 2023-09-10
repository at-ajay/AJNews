package com.aj.ajnews.data.repository

import com.aj.ajnews.data.remote.NewsApi
import com.aj.ajnews.data.remote.dto.ArticlesResponse
import com.aj.ajnews.domain.repositiry.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (private val api: NewsApi): NewsRepository {

    override suspend fun getTopHeadLines(
        country: String,
        page: Int
    ): ArticlesResponse {
        return api.getTopHeadlines(country, page)
    }

    override suspend fun getTopPicks(
        country: String,
        category: String,
        count: Int
    ): ArticlesResponse {
        return api.getTopPicks(country, category, count)
    }

    override suspend fun searchNews(
        searchQuery: String,
        page: Int
    ): ArticlesResponse {
        return api.searchNews(searchQuery, page)
    }

}