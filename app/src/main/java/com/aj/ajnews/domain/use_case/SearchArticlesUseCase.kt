package com.aj.ajnews.domain.use_case

import androidx.compose.runtime.mutableStateOf
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.domain.repositiry.NewsRepository
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Result
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(private val localRepository: SavedArticlesRepository, private val repository: NewsRepository) {

    operator fun invoke(query: String, page: Int) = flow {
        try {
            if (query.isBlank()) {
                emit(Result.Success(data = listOf<ArticleInfo>()))
                return@flow
            }
            emit(Result.Loading())
            val response = repository.searchNews(searchQuery = query, page = page).articles
            response.removeAll { it.title == null || it.title == "[Removed]" }

            val articles = mutableListOf<ArticleInfo>()
            response.forEach { article ->
                articles.add(
                    ArticleInfo(
                        title = article.title!!,
                        description = article.description,
                        imageUrl = article.urlToImage,
                        url = article.url!!,
                        isSaved = mutableStateOf(localRepository.getArticleByUrl(article.url).isNotEmpty())
                    )
                )
            }

            emit(Result.Success(data = articles))
        } catch (e: HttpException) {
            if (e.localizedMessage?.trim() == "HTTP 429") {
                emit(Result.Failure(error = "Request limit reached. Please try again later"))
            } else {
                emit(Result.Failure(error = e.localizedMessage ?: "An unexpected error occurred"))
            }
        } catch (e: IOException) {
            emit(Result.Failure(error = "Couldn't reach server, please try again later"))
        }
    }

}