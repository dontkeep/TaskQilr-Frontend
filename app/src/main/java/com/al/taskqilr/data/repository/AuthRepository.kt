package com.al.taskqilr.data.repository

import com.al.taskqilr.data.network.ApiServices

class AuthRepository(private val apiService: ApiServices) {
    suspend fun login(): String? {
        val response = apiService.login()
        return if (response.isSuccessful) {
            response.body()?.get("token")
        } else null
    }
}