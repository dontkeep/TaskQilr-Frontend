package com.al.taskqilr.domain.usecase

import com.al.taskqilr.data.repository.AuthRepository
import jakarta.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String? {
        return authRepository.login()
    }
}