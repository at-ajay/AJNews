package com.aj.ajnews.data.remote

import com.aj.ajnews.BuildConfig
import com.aj.ajnews.data.remote.dto.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ArticlesResponse

    @GET("v2/top-headlines")
    suspend fun getTopPicks(
        @Query("country") country: String = "in",
        @Query("category") category: String,
        @Query("pageSize") count: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ArticlesResponse

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): ArticlesResponse

}