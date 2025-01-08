package com.al.taskqilr.di

import com.al.taskqilr.data.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.137.1:8080") // Replace with your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)
}