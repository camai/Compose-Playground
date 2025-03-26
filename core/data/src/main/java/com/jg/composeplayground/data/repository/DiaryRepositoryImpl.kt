package com.jg.composeplayground.data.repository

import com.jg.composeplayground.data.database.DiaryLocalDataSource
import com.jg.composeplayground.domain.model.Diary
import com.jg.composeplayground.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val localDataSource: DiaryLocalDataSource
) : DiaryRepository {

    override fun getAllDiaries(): Flow<List<Diary>> {
        return localDataSource.getAllDiaries()
    }

    override suspend fun getDiaryById(diaryId: Int): Diary? {
        return localDataSource.getDiaryById(diaryId)
    }

    override suspend fun saveDiary(diary: Diary): Long {
        return localDataSource.insertDiary(diary)
    }

    override suspend fun updateDiary(diary: Diary) {
        localDataSource.updateDiary(diary)
    }

    override suspend fun deleteDiary(diaryId: Int) {
        localDataSource.deleteDiary(diaryId)
    }
} 