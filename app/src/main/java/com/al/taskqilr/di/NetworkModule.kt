package com.al.taskqilr.di

import com.al.taskqilr.data.network.ApiServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("192.168.137.1:8080") // Replace with your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideAuthApi(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)
}