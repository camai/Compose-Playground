package com.jg.composeplayground.domain.usecase.diary

import android.os.Build
import com.jg.composeplayground.domain.repository.DiaryRepository
import java.time.LocalDateTime
import javax.inject.Inject


interface UpdateDiaryUseCase {
    suspend operator fun invoke(diaryId: Int, content: String)
}

class UpdateDiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository
): UpdateDiaryUseCase {
    override suspend fun invoke(diaryId: Int, content: String) {
        val existingDiary = diaryRepository.getDiaryById(diaryId) ?: return
        val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val updatedDiary = existingDiary.copy(
            content = content,
            updatedAt = now
        )
        diaryRepository.updateDiary(updatedDiary)
    }
}