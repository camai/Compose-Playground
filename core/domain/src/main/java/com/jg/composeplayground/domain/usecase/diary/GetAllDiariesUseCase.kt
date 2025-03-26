package com.jg.composeplayground.domain.usecase.diary

import com.jg.composeplayground.domain.model.Diary
import com.jg.composeplayground.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 모든 일기 목록을 가져오는 UseCase
 */
interface GetAllDiariesUseCase {
    operator fun invoke(): Flow<List<Diary>>
}

class GetAllDiariesUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository
): GetAllDiariesUseCase {
    override fun invoke(): Flow<List<Diary>> {
        return diaryRepository.getAllDiaries()
    }
}