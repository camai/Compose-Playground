package com.jg.composeplayground.database.source

import com.jg.composeplayground.data.database.CalculatorHistoryLocalDataSource
import com.jg.composeplayground.database.dao.CalculatorHistoryDao
import com.jg.composeplayground.database.entity.CalculatorHistoryEntity
import com.jg.composeplayground.model.data.CalculatorHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CalculatorHistoryLocalDataSourceImpl @Inject constructor(
  private val calculatorHistoryDao: CalculatorHistoryDao
): CalculatorHistoryLocalDataSource {
    override fun getAllHistories(): Flow<List<CalculatorHistory>> {
        return calculatorHistoryDao.getAllHistories().map { entities ->
            entities.map { it.toCalculatorHistory() }
        }
    }

    override suspend fun insertHistory(history: CalculatorHistory) {
        return calculatorHistoryDao.insertHistory(history.toEntity())
    }

    override suspend fun deleteAllHistory() {
        return calculatorHistoryDao.deleteAllHistory()
    }

    private fun CalculatorHistoryEntity.toCalculatorHistory(): CalculatorHistory {
        return CalculatorHistory(
            id = this.id,
            expression = this.expression,
            result = this.result
        )
    }

    private fun  CalculatorHistory.toEntity(): CalculatorHistoryEntity {
        return CalculatorHistoryEntity(
            id = this.id,
            expression = this.expression,
            result = this.result
        )
    }
}