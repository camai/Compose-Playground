package com.jg.composeplayground.domain.repository

import com.jg.composeplayground.model.data.CalculatorHistory
import kotlinx.coroutines.flow.Flow

interface CalculatorHistoryRepository {
    fun getAllHistories(): Flow<List<CalculatorHistory>>

    suspend fun saveHistory(history: CalculatorHistory)

    suspend fun deleteAllHistory()
}