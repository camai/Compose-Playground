package com.jg.composeplayground.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jg.composeplayground.database.entity.DiaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryEntity): Long
    
    @Update
    suspend fun updateDiary(diary: DiaryEntity)
    
    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)
    
    @Query("SELECT * FROM diaries ORDER BY createdAt DESC")
    fun getAllDiaries(): Flow<List<DiaryEntity>>
    
    @Query("SELECT * FROM diaries WHERE id = :diaryId")
    suspend fun getDiaryById(diaryId: Int): DiaryEntity?
    
    @Query("DELETE FROM diaries WHERE id = :diaryId")
    suspend fun deleteDiaryById(diaryId: Int)
} 