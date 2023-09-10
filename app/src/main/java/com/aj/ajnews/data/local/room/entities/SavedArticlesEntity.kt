package com.aj.ajnews.data.local.room.entities

import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aj.ajnews.util.ArticleInfo

@Entity
data class SavedArticlesEntity (
    val title: String,
    val description: String?,
    val imageUrl: String?,
    @PrimaryKey val url: String
) {

    fun toArticleInfo(): ArticleInfo {
        return ArticleInfo(
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl,
            url = this.url,
            isSaved = mutableStateOf(true)
        )
    }

}
