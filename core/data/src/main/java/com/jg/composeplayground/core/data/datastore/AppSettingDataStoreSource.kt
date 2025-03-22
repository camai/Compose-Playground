package com.jg.composeplayground.core.data.datastore

import com.jg.composeplayground.core.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow

/**
 * 앱 설정 데이터 스토어에 접근하기 위한 인터페이스
 */
interface AppSettingDataStoreSource {
    /**
     * 앱 설정 정보를 Flow로 제공
     */
    fun getAppSetting(): Flow<AppSetting>
    
    /**
     * 패스코드 설정
     */
    suspend fun setPassCode(passCode: String)
} 