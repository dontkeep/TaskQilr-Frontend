package com.al.taskqilr.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppApiConfig {
    private val BASE_URL = "https://precious-sure-lioness.ngrok-free.app"

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}