package com.jg.composeplayground.domain.usecase.diary

import android.os.Build
import com.jg.composeplayground.domain.model.Diary
import com.jg.composeplayground.domain.repository.DiaryRepository
import java.time.LocalDateTime
import javax.inject.Inject


interface SaveDiaryUseCase {
    suspend operator fun invoke(content: String): Long
}

class SaveDiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository
): SaveDiaryUseCase {
    override suspend fun invoke(content: String): Long {
        val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val diary = Diary(
            content = content,
            createdAt = now,
            updatedAt = now
        )
        return diaryRepository.saveDiary(diary)
    }
}