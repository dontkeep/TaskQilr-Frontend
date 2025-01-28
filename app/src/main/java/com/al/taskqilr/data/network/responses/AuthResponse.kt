package com.al.taskqilr.data.network.responses

data class AuthResponse(
    val token: String,
    val user: UserData
)

data class UserData(
    val id: String,
    val firebaseUid: String,
    val email: String,
    val createdAt: String,
)