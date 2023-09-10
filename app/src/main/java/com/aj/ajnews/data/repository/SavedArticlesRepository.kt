package com.aj.ajnews.data.repository

import com.aj.ajnews.data.local.room.SavedArticlesDao
import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity
import kotlinx.coroutines.flow.Flow

class SavedArticlesRepository(private val dao: SavedArticlesDao) {

    suspend fun upsertArticle(article: SavedArticlesEntity) {
        dao.upsertArticle(article)
    }

    suspend fun deleteArticle(article: SavedArticlesEntity) {
        dao.deleteArticle(article)
    }

    fun getSavedArticles(): Flow<List<SavedArticlesEntity>> {
        return dao.getSavedArticles()
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun getArticleByUrl(url: String): List<SavedArticlesEntity> {
        return dao.getArticleByUrl(url)
    }

}