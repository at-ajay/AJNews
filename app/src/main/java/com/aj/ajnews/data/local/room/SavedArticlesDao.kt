package com.aj.ajnews.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticlesDao {

    @Upsert
    suspend fun upsertArticle(article: SavedArticlesEntity)

    @Delete
    suspend fun deleteArticle(article: SavedArticlesEntity)

    @Query("SELECT * FROM SavedArticlesEntity")
    fun getSavedArticles(): Flow<List<SavedArticlesEntity>>

    @Query("DELETE FROM SavedArticlesEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM SavedArticlesEntity WHERE url = :url")
    suspend fun getArticleByUrl(url: String): List<SavedArticlesEntity>

}