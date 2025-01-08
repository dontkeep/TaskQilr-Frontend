package com.al.taskqilr.domain.usecase

import com.al.taskqilr.data.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String? {
        return authRepository.login()
    }
}