package com.al.taskqilr.di

import com.al.taskqilr.data.network.AppApiConfig
import com.al.taskqilr.data.network.AppApiServices
import com.al.taskqilr.data.repository.DataRepository
import com.al.taskqilr.domain.manager.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://precious-sure-lioness.ngrok-free.app"
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return AppApiConfig().retrofit
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AppApiServices {
        return retrofit.create(AppApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideDataRepository(apiService: AppApiServices): DataRepository {
        return DataRepository(apiService)
    }
}