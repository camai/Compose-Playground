package com.jg.composeplayground.core.data.repository

import com.jg.composeplayground.data.model.AppSettingDasto
import com.jg.composeplayground.data.datastore.AppSettingDataStoreSource
import com.jg.composeplayground.data.repository.AppSettingRepositoryImpl
import com.jg.composeplayground.domain.model.AppSetting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingRepositoryImplTest {

    private lateinit var appSettingDataStoreSource: AppSettingDataStoreSource
    private lateinit var appSettingRepository: AppSettingRepositoryImpl

    // 테스트 데이터
    private val testPassCode = "1234"
    private val testAppSettingDasto = AppSettingDasto(passCode = testPassCode)
    private val testAppSetting = AppSetting(passCode = testPassCode)

    @Before
    fun setup() {
        // Mock 데이터 소스 생성
        appSettingDataStoreSource = mock()
        // 설정 Flow를 모의로 반환하도록 설정
        whenever(appSettingDataStoreSource.settings).thenReturn(flowOf(testAppSettingDasto))
        
        // 테스트 대상 객체 생성
        appSettingRepository = AppSettingRepositoryImpl(appSettingDataStoreSource)
    }

    @Test
    fun `settings 프로퍼티는 데이터 소스의 settings를 도메인 모델로 변환하여 반환해야 함`() = runTest {
        // When: settings 프로퍼티에 접근하면
        val result = appSettingRepository.settings.first()
        
        // Then: 변환된 도메인 모델이 반환되어야 함
        assertEquals(testAppSetting, result)
    }

    @Test
    fun `setAppSetting은 데이터 소스의 setPassCode를 호출해야 함`() = runTest {
        // When: setAppSetting 메서드 호출 시
        appSettingRepository.setAppSetting(testPassCode)
        
        // Then: 데이터 소스의 setPassCode가 호출되어야 함
        verify(appSettingDataStoreSource).setPassCode(testPassCode)
    }
} 