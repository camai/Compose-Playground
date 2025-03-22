package com.jg.composeplayground.core.data.di

import com.jg.composeplayground.core.data.repository.AppSettingRepositoryImpl
import com.jg.composeplayground.core.domain.repository.AppSettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindAppSettingRepository(
        appSettingRepositoryImpl: AppSettingRepositoryImpl
    ): AppSettingRepository
} 