package com.jg.composeplayground.data.di

import com.jg.composeplayground.data.repository.AppSettingRepositoryImpl
import com.jg.composeplayground.data.repository.CalculatorHistoryRepositoryImpl
import com.jg.composeplayground.data.repository.DiaryRepositoryImpl
import com.jg.composeplayground.domain.repository.AppSettingRepository
import com.jg.composeplayground.domain.repository.CalculatorHistoryRepository
import com.jg.composeplayground.domain.repository.DiaryRepository
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
        appSettingRepository: AppSettingRepositoryImpl
    ): AppSettingRepository

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(
        diaryRepository: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindCalculatorHistoryRepository(
        calculatorHistoryRepository: CalculatorHistoryRepositoryImpl
    ): CalculatorHistoryRepository

} 