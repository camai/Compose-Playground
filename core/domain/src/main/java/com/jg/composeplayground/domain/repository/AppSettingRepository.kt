package com.jg.composeplayground.domain.repository

import com.jg.composeplayground.model.data.AppSetting
import kotlinx.coroutines.flow.Flow

interface AppSettingRepository {

    val settings: Flow<AppSetting>

    suspend fun setAppSetting(value: String)
}