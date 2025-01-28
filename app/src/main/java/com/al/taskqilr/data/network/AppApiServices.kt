package com.al.taskqilr.data.network

import com.al.taskqilr.data.network.responses.AuthResponse
import com.al.taskqilr.data.network.responses.LogoutResponse
import com.google.android.gms.auth.api.Auth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AppApiServices {
    @POST("/auth/login")
    @FormUrlEncoded
    fun auth(
        @Field("idToken") idToken: String
    ): Call<AuthResponse>

    @POST("/auth/logout")
    fun logout(
        @Header("Authorization") token: String
    ): Call<LogoutResponse>
}