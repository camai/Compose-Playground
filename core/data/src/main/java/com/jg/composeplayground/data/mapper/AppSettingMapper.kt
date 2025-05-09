package com.jg.composeplayground.data.mapper

import com.jg.composeplayground.data.model.AppSettingDasto
import com.jg.composeplayground.model.data.AppSetting

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