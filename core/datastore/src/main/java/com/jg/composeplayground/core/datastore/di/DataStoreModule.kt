package com.jg.composeplayground.core.datastore.di

import com.jg.composeplayground.core.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.core.datastore.AppSettingDataStoreSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

private const val APP_PREFERENCES = "app_preferences"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppSettingDataStore

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreBindModule {

    @Binds
    @Singleton
    abstract fun bindAppSettingDataStoreSource(
        appSettingDataStoreSourceImpl: AppSettingDataStoreSourceImpl
    ): AppSettingDataStoreSource
} 