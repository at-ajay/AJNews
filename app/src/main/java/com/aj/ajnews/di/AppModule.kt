package com.aj.ajnews.di

import android.app.Application
import androidx.room.Room
import com.aj.ajnews.data.local.datastore.DataStore
import com.aj.ajnews.data.local.room.SavedArticlesDatabase
import com.aj.ajnews.data.remote.NewsApi
import com.aj.ajnews.data.repository.NewsRepositoryImpl
import com.aj.ajnews.data.repository.SavedArticlesRepository
import com.aj.ajnews.domain.repositiry.NewsRepository
import com.aj.ajnews.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Application) = DataStore(context)

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewsApi::class.java)

    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSavedArticlesDatabase(app: Application): SavedArticlesDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = SavedArticlesDatabase::class.java,
            name = "Saved Articles Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSavedArticlesRepository(db: SavedArticlesDatabase): SavedArticlesRepository {
        return SavedArticlesRepository(db.dao)
    }

}