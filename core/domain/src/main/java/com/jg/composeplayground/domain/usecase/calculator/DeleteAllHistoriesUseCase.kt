package com.jg.composeplayground.domain.usecase.calculator

import com.jg.composeplayground.domain.repository.CalculatorHistoryRepository
import javax.inject.Inject

interface DeleteAllHistoriesUseCase {
    suspend operator fun invoke()
}

class DeleteAllHistoriesUseCaseImpl @Inject constructor(
    private val calculatorHistoryRepository: CalculatorHistoryRepository
): DeleteAllHistoriesUseCase {
    override suspend fun invoke() {
        calculatorHistoryRepository.deleteAllHistory()
    }
}