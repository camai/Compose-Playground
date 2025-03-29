package com.jg.composeplayground.domain.repository

import com.jg.composeplayground.model.data.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAllDiaries(): Flow<List<Diary>>
    
    suspend fun getDiaryById(diaryId: Int): Diary?
    
    suspend fun saveDiary(diary: Diary): Long
    
    suspend fun updateDiary(diary: Diary)
    
    suspend fun deleteDiary(diaryId: Int)
} 