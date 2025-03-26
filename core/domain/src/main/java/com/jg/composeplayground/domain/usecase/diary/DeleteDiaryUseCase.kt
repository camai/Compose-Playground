package com.jg.composeplayground.domain.usecase.diary

import com.jg.composeplayground.domain.repository.DiaryRepository
import javax.inject.Inject


interface DeleteDiaryUseCase {
    suspend operator fun invoke(diaryId: Int)
}

class DeleteDiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository
): DeleteDiaryUseCase {
    override suspend fun invoke(diaryId: Int) {
        diaryRepository.deleteDiary(diaryId)
    }
}
