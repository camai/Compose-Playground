package com.jg.composeplayground.data.repository

import com.jg.composeplayground.data.database.CalculatorHistoryLocalDataSource
import com.jg.composeplayground.domain.repository.CalculatorHistoryRepository
import com.jg.composeplayground.model.data.CalculatorHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalculatorHistoryRepositoryImpl @Inject constructor(
    private val localDataSource: CalculatorHistoryLocalDataSource
): CalculatorHistoryRepository {
    override fun getAllHistories(): Flow<List<CalculatorHistory>> {
        return localDataSource.getAllHistories()
    }

    override suspend fun saveHistory(history: CalculatorHistory) {
        return localDataSource.insertHistory(history)
    }

    override suspend fun deleteAllHistory() {
        return localDataSource.deleteAllHistory()
    }
}