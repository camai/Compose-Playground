package com.jg.composeplayground.domain.usecase.calculator

import com.jg.composeplayground.domain.repository.CalculatorHistoryRepository
import com.jg.composeplayground.model.data.CalculatorHistory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllHistoriesUseCase {
    operator fun invoke(): Flow<List<CalculatorHistory>>
}

class GetAllHistoriesUseCaseImpl @Inject constructor(
    private val calculatorHistoryRepository: CalculatorHistoryRepository
): GetAllHistoriesUseCase {
    override operator fun invoke(): Flow<List<CalculatorHistory>> {
        return calculatorHistoryRepository.getAllHistories()
    }
}