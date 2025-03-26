package com.jg.composeplayground.domain.usecase.diary

import com.jg.composeplayground.domain.model.Diary
import com.jg.composeplayground.domain.repository.DiaryRepository
import javax.inject.Inject


interface GetDiaryByIdUseCase {
    suspend operator fun invoke(diaryId: Int): Diary?
}

class GetDiaryByIdUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository
): GetDiaryByIdUseCase {
    override suspend fun invoke(diaryId: Int): Diary? {
        return diaryRepository.getDiaryById(diaryId)
    }
}
