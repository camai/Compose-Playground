package com.jg.composeplayground.core.data.mapper

import com.jg.composeplayground.core.data.model.AppSettingDasto
import com.jg.composeplayground.core.domain.model.AppSetting

object AppSettingMapper {

    internal fun AppSettingDasto.toModel(): AppSetting =
        AppSetting(
            passCode = passCode
        )

    internal fun AppSetting.toDasto(): AppSettingDasto =
        AppSettingDasto(
            passCode = passCode
        )
}