package com.jg.composeplayground.core.domain.repository

import com.jg.composeplayground.core.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow

interface AppSettingRepository {

    val settings: Flow<AppSetting>

    suspend fun setAppSetting(value: String)
}