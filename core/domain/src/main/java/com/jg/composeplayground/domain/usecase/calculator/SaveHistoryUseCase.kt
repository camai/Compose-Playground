package com.jg.composeplayground.domain.usecase.calculator

import com.jg.composeplayground.domain.repository.CalculatorHistoryRepository
import com.jg.composeplayground.model.data.CalculatorHistory
import javax.inject.Inject

interface SaveHistoryUseCase {
    suspend operator fun invoke(history: CalculatorHistory)
}

class SaveHistoryUseCaseImpl @Inject constructor(
    private val calculatorHistoryRepository: CalculatorHistoryRepository
): SaveHistoryUseCase {
    override suspend fun invoke(history: CalculatorHistory) {
        calculatorHistoryRepository.saveHistory(history)
    }
}