package com.jg.composeplayground.datastore.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jg.composeplayground.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.datastore.di.AppSettingDataStore
import com.jg.composeplayground.datastore.utils.asDataStoreFlow
import com.jg.composeplayground.model.data.AppSettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * AppSettings DataStore 구현체
 * data 모듈의 AppSettingDataStoreSource 인터페이스 구현
 */
class AppSettingsDataSourceImpl @Inject constructor(
    @AppSettingDataStore private val dataStore: DataStore<Preferences>
) : AppSettingDataStoreSource {

    private object PreferencesKeys {
        val PASS_CODE = stringPreferencesKey("pass_code")
    }

    override val settings: Flow<AppSettingData> = dataStore.data
        .map { preferences ->
            AppSettingData(
                passCode = preferences[PreferencesKeys.PASS_CODE] ?: ""
            )
        }.asDataStoreFlow("app-settings", AppSettingData())

    override suspend fun setPassCode(passCode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PASS_CODE] = passCode
        }
    }
} 