package com.jg.composeplayground.core.domain.di

import com.jg.composeplayground.core.domain.usecase.GetPassCodeUseCase
import com.jg.composeplayground.core.domain.usecase.GetPassCodeUseCaseImpl
import com.jg.composeplayground.core.domain.usecase.SetPassCodeUseCase
import com.jg.composeplayground.core.domain.usecase.SetPassCodeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModules {

    @Binds
    abstract fun bindGetPassCodeUseCase(
        getPassCodeUseCaseImpl: GetPassCodeUseCaseImpl
    ): GetPassCodeUseCase

    @Binds
    abstract fun bindSetPassCodeUseCase(
        setPassCodeUseCaseImpl: SetPassCodeUseCaseImpl
    ): SetPassCodeUseCase
}
