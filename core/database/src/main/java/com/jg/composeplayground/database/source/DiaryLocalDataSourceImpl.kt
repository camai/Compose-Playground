package com.jg.composeplayground.database.source

import android.os.Build
import com.jg.composeplayground.data.database.DiaryLocalDataSource
import com.jg.composeplayground.database.dao.DiaryDao
import com.jg.composeplayground.database.entity.DiaryEntity
import com.jg.composeplayground.domain.model.Diary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * DiaryLocalDataSource 인터페이스 구현체
 * Room DAO를 통해 일기 데이터에 접근합니다.
 */
class DiaryLocalDataSourceImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryLocalDataSource {

    override fun getAllDiaries(): Flow<List<Diary>> {
        return diaryDao.getAllDiaries().map { entities ->
            entities.map { it.toDiary() }
        }
    }

    override suspend fun getDiaryById(diaryId: Int): Diary? {
        return diaryDao.getDiaryById(diaryId)?.toDiary()
    }

    override suspend fun insertDiary(diary: Diary): Long {
        return diaryDao.insertDiary(diary.toEntity())
    }

    override suspend fun updateDiary(diary: Diary) {
        diaryDao.updateDiary(diary.toEntity())
    }

    override suspend fun deleteDiary(diaryId: Int) {
        diaryDao.deleteDiaryById(diaryId)
    }

    // 엔티티 <-> 도메인 변환 확장 함수
    private fun DiaryEntity.toDiary(): Diary {
        return Diary(
            id = this.id,
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    private fun Diary.toEntity(): DiaryEntity {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DiaryEntity(
                id = this.id,
                content = this.content,
                createdAt = this.createdAt ?: LocalDateTime.now(),
                updatedAt = this.updatedAt ?: LocalDateTime.now()
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
} 