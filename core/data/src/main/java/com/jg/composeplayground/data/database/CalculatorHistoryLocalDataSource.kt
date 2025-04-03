package com.jg.composeplayground.data.database

import com.jg.composeplayground.model.data.CalculatorHistory
import kotlinx.coroutines.flow.Flow

/**
 * 계산기 히스토리 데이터에 대한 로컬 데이터소스 인터페이스
 * database 모듈에서 구현합니다.
 */
interface CalculatorHistoryLocalDataSource {
    fun getAllHistories(): Flow<List<CalculatorHistory>>

    suspend fun insertHistory(history: CalculatorHistory)

    suspend fun deleteAllHistory()
}