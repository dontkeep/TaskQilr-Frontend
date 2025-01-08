package com.al.taskqilr.data.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("/login")
    suspend fun login(): Response<Map<String, String>>
}