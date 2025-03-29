package com.jg.composeplayground.data.database

import com.jg.composeplayground.model.data.Diary
import kotlinx.coroutines.flow.Flow

/**
 * 일기 데이터에 대한 로컬 데이터소스 인터페이스
 * database 모듈에서 구현합니다.
 */
interface DiaryLocalDataSource {
    fun getAllDiaries(): Flow<List<Diary>>
    
    suspend fun getDiaryById(diaryId: Int): Diary?
    
    suspend fun insertDiary(diary: Diary): Long
    
    suspend fun updateDiary(diary: Diary)
    
    suspend fun deleteDiary(diaryId: Int)
} 