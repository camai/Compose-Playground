package com.jg.composeplayground.core.data.mapper

import com.jg.composeplayground.core.data.mapper.AppSettingMapper.toDasto
import com.jg.composeplayground.core.data.mapper.AppSettingMapper.toModel
import com.jg.composeplayground.core.data.model.AppSettingDasto
import com.jg.composeplayground.core.domain.model.AppSetting
import org.junit.Assert.assertEquals
import org.junit.Test

class AppSettingMapperTest {

    private val testPassCode = "9876"
    private val appSettingDasto = AppSettingDasto(passCode = testPassCode)
    private val appSetting = AppSetting(passCode = testPassCode)

    @Test
    fun `AppSettingDasto를 AppSetting으로 변환 테스트`() {
        // When: AppSettingDasto를 AppSetting으로 변환하면
        val result = appSettingDasto.toModel()

        // Then: 필드값이 올바르게 매핑되어야 함
        assertEquals(appSetting.passCode, result.passCode)
        assertEquals(appSetting, result)
    }

    @Test
    fun `AppSetting을 AppSettingDasto로 변환 테스트`() {
        // When: AppSetting을 AppSettingDasto로 변환하면
        val result = appSetting.toDasto()

        // Then: 필드값이 올바르게 매핑되어야 함
        assertEquals(appSettingDasto.passCode, result.passCode)
        assertEquals(appSettingDasto, result)
    }
} 