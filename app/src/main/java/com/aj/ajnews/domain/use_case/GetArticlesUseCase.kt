package com.aj.ajnews.domain.use_case

import androidx.compose.runtime.mutableStateOf
import com.aj.ajnews.data.local.datastore.DataStore
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.domain.repositiry.NewsRepository
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Constants.DEFAULT_COUNTRY_CODE
import com.aj.ajnews.util.Result
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val dataStore: DataStore, private val repository: NewsRepository, private val localRepository: SavedArticlesRepository) {

    operator fun invoke(page: Int) = flow {
        try {
            emit(Result.Loading())
            val country = dataStore.getCountryCode() ?: DEFAULT_COUNTRY_CODE
            val response = repository.getTopHeadLines(country, page).articles
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

            emit(Result.Success(data = articles.toList()))
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