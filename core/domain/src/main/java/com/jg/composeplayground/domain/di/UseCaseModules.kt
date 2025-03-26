package com.jg.composeplayground.domain.di

import com.jg.composeplayground.domain.usecase.diary.DeleteDiaryUseCase
import com.jg.composeplayground.domain.usecase.diary.DeleteDiaryUseCaseImpl
import com.jg.composeplayground.domain.usecase.diary.GetAllDiariesUseCase
import com.jg.composeplayground.domain.usecase.diary.GetAllDiariesUseCaseImpl
import com.jg.composeplayground.domain.usecase.diary.GetDiaryByIdUseCase
import com.jg.composeplayground.domain.usecase.diary.GetDiaryByIdUseCaseImpl
import com.jg.composeplayground.domain.usecase.diary.SaveDiaryUseCase
import com.jg.composeplayground.domain.usecase.diary.SaveDiaryUseCaseImpl
import com.jg.composeplayground.domain.usecase.diary.UpdateDiaryUseCase
import com.jg.composeplayground.domain.usecase.diary.UpdateDiaryUseCaseImpl
import com.jg.composeplayground.domain.usecase.passcode.GetPassCodeUseCase
import com.jg.composeplayground.domain.usecase.passcode.GetPassCodeUseCaseImpl
import com.jg.composeplayground.domain.usecase.passcode.SetPassCodeUseCase
import com.jg.composeplayground.domain.usecase.passcode.SetPassCodeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModules {

    @Binds
    abstract fun bindGetPassCodeUseCase(
        getPassCodeUseCase: GetPassCodeUseCaseImpl
    ): GetPassCodeUseCase

    @Binds
    abstract fun bindSetPassCodeUseCase(
        setPassCodeUseCase: SetPassCodeUseCaseImpl
    ): SetPassCodeUseCase

    @Binds
    abstract fun bindGetDiaryByIdUseCase(
        getDiaryByIdUseCase: GetDiaryByIdUseCaseImpl
    ): GetDiaryByIdUseCase


    @Binds
    abstract fun bindDeleteDiaryUseCase(
        deleteDiaryUseCase: DeleteDiaryUseCaseImpl
    ): DeleteDiaryUseCase

    @Binds
    abstract fun bindUpdateDiaryUseCase(
        updateDiaryUseCase: UpdateDiaryUseCaseImpl
    ): UpdateDiaryUseCase

    @Binds
    abstract fun bindSaveDiaryUseCase(
        saveDiaryUseCase: SaveDiaryUseCaseImpl
    ): SaveDiaryUseCase

    @Binds
    abstract fun bindGetAllDiariesUseCase(
        getAllDiariesUseCase: GetAllDiariesUseCaseImpl
    ): GetAllDiariesUseCase

}
