package com.al.taskqilr.di

import com.al.taskqilr.data.network.ApiServices
import com.al.taskqilr.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAuthRepository(apiService: ApiServices): AuthRepository {
        return AuthRepository(apiService)
    }
}