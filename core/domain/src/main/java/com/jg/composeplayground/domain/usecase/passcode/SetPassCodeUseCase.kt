package com.jg.composeplayground.domain.usecase.passcode

import com.jg.composeplayground.domain.repository.AppSettingRepository
import javax.inject.Inject

interface SetPassCodeUseCase {
    suspend operator fun invoke(passCode: String)
}

class SetPassCodeUseCaseImpl @Inject constructor(
    private val appSettingRepository: AppSettingRepository
) : SetPassCodeUseCase {
    override suspend operator fun invoke(passCode: String) {
        appSettingRepository.setAppSetting(value = passCode)
    }
}