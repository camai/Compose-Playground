package com.jg.composeplayground.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jg.composeplayground.core.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.core.domain.model.AppSetting
import com.jg.composeplayground.core.datastore.di.AppSettingDataStore
import com.jg.composeplayground.core.datastore.utils.asDataStoreFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingDataStoreSourceImpl @Inject constructor(
    @AppSettingDataStore private val dataStore: DataStore<Preferences>
) : AppSettingDataStoreSource {

    private object PreferencesKeys {
        val PASS_CODE = stringPreferencesKey("pass_code")
    }

    override fun getAppSetting(): Flow<AppSetting> = dataStore.data
        .map { preferences ->
            AppSetting(
                passCode = preferences[PreferencesKeys.PASS_CODE] ?: ""
            )
        }.asDataStoreFlow("app-settings", defaultValue = AppSetting())

    override suspend fun setPassCode(passCode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PASS_CODE] = passCode
        }
    }
} 