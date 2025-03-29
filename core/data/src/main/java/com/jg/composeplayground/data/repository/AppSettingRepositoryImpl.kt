package com.jg.composeplayground.data.repository

import com.jg.composeplayground.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.domain.repository.AppSettingRepository
import com.jg.composeplayground.model.data.AppSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingRepositoryImpl @Inject constructor(
    private val appSettingDataStoreSource: AppSettingDataStoreSource
) : AppSettingRepository {
    
    override val settings: Flow<AppSetting> =
        appSettingDataStoreSource.settings.map { AppSetting(it.passCode) }

    override suspend fun setAppSetting(value: String) {
        appSettingDataStoreSource.setPassCode(value)
    }
}