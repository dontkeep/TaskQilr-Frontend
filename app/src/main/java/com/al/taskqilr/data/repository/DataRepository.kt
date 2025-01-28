package com.al.taskqilr.data.repository

import com.al.taskqilr.data.network.AppApiServices
import com.al.taskqilr.data.network.responses.AuthResponse
import com.al.taskqilr.data.network.responses.LogoutResponse
import retrofit2.Call
import javax.inject.Inject


class DataRepository @Inject constructor(private val apiService: AppApiServices) {
    fun authenticate(idToken: String): Call<AuthResponse> {
        return apiService.auth(idToken)
    }

    fun logout(token: String): Call<LogoutResponse> {
        return apiService.logout("Bearer $token")
    }
}