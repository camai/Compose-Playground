// 이 파일은 더 이상 사용되지 않습니다.
// 대신 datastore 모듈의 AppSettingsDataSource를 사용합니다. 

package com.jg.composeplayground.data.datastore

import com.jg.composeplayground.model.data.AppSetting
import kotlinx.coroutines.flow.Flow

/**
 * 앱 설정 데이터 스토어에 접근하기 위한 인터페이스
 * Repository에서 DataStore로 접근하기 위한 계약 정의
 */
interface AppSettingDataStoreSource {
    /**
     * 앱 설정 정보를 Flow로 제공
     */
    val settings: Flow<AppSetting>
    
    /**
     * 패스코드 설정
     */
    suspend fun setPassCode(passCode: String)
} 