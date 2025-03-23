package com.jg.composeplayground.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jg.composeplayground.core.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.core.datastore.setting.AppSettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppSettingDataStore

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val APP_SETTING_DATA_STORE = "app_setting.ds"

    @Singleton
    @AppSettingDataStore
    @Provides
    fun provideAppSettingDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(APP_SETTING_DATA_STORE) },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreBindModule {

    @Binds
    @Singleton
    abstract fun bindAppSettingsDataSource(
        appSettingsDataSourceImpl: AppSettingsDataSourceImpl
    ): AppSettingDataStoreSource
} 