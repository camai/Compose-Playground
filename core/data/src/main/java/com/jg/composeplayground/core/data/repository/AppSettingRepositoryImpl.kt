package com.jg.composeplayground.core.data.repository

import com.jg.composeplayground.core.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.core.domain.model.AppSetting
import com.jg.composeplayground.core.domain.repository.AppSettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppSettingRepositoryImpl @Inject constructor(
    private val appSettingDataStoreSource: AppSettingDataStoreSource
) : AppSettingRepository {
    
    override val settings: Flow<AppSetting> =
        appSettingDataStoreSource.getAppSetting()

    override suspend fun setAppSetting(value: String) {
        appSettingDataStoreSource.setPassCode(value)
    }
}