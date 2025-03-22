package com.jg.composeplayground.core.domain.usecase

import com.jg.composeplayground.core.domain.repository.AppSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


interface GetPassCodeUseCase {
    operator fun invoke(): Flow<String>
}

class GetPassCodeUseCaseImpl @Inject constructor(
    private val appSettingRepository: AppSettingRepository
) : GetPassCodeUseCase{
    override fun invoke(): Flow<String> {
        return appSettingRepository.settings.map {
            it.passCode
        }
    }
}