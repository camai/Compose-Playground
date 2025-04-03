package com.jg.composeplayground.calculator.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.composeplayground.domain.usecase.calculator.DeleteAllHistoriesUseCase
import com.jg.composeplayground.domain.usecase.calculator.GetAllHistoriesUseCase
import com.jg.composeplayground.domain.usecase.calculator.SaveHistoryUseCase
import com.jg.composeplayground.model.data.CalculatorHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val getAllHistoriesUseCase: GetAllHistoriesUseCase,
    private val deleteAllHistoriesUseCase: DeleteAllHistoriesUseCase,
    private val saveHistoryUseCase: SaveHistoryUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState = _uiState.asStateFlow()

    private val _histories: StateFlow<List<CalculatorHistory>> = getAllHistoriesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val histories = _histories

    private val calculator = Calculator()

    fun inputValue(value: Char) {
        when {
            isInputLengthExceeded() -> updateUiState { it.copy(isOverLength = true) }
            isEmptyInputWithZero(value) -> updateUiState { it.copy(isZero = true) }
            isSpecialOperation(value) -> handleSpecialOperation(value)
            isOperator(value) -> handleOperatorInput(value)
            else -> handleNumberInput(value)
        }
    }

    private fun isInputLengthExceeded(): Boolean {
        return _uiState.value.expressionValue.text.isNotEmpty() &&
                _uiState.value.expressionValue.text.length >= 15
    }

    private fun isEmptyInputWithZero(value: Char): Boolean {
        return _uiState.value.expressionValue.text.isEmpty() && value == '0'
    }

    private fun isSpecialOperation(value: Char): Boolean {
        return value == Operator.CLEAR.value || value == Operator.EQUAL.value
    }

    private fun handleSpecialOperation(value: Char) {
        when (value) {
            Operator.CLEAR.value -> clearInput()
            Operator.EQUAL.value -> calculateFinalResult()
        }
    }

    private fun handleOperatorInput(value: Char) {
        val currentState = _uiState.value
        val currentExpression = currentState.expressionValue.text

        when {
            currentState.isOperator -> replaceLastOperator(currentExpression, value)
            currentState.hasOperator -> updateUiState { it.copy(isSameOperator = true) }
            else -> appendOperator(currentExpression, value)
        }
    }

    private fun replaceLastOperator(currentExpression: String, operator: Char) {
        val newExpression = currentExpression.dropLast(1) + operator
        updateExpressionWithHighlightedOperator(newExpression, operator)
    }

    private fun appendOperator(currentExpression: String, operator: Char) {
        val newExpression = "$currentExpression $operator"
        updateExpressionWithHighlightedOperator(newExpression, operator)
    }

    private fun updateExpressionWithHighlightedOperator(expression: String, operator: Char) {
        val operatorIndex = expression.lastIndexOf(operator)
        if (operatorIndex < 0) return

        val annotatedString = buildAnnotatedString {
            append(expression)
            addStyle(
                style = SpanStyle(color = Color.Green),
                start = operatorIndex,
                end = operatorIndex + 1
            )
        }

        updateUiState {
            it.copy(
                expressionValue = annotatedString,
                isOperator = true,
                hasOperator = true
            )
        }

        updateCalculation()
    }

    private fun handleNumberInput(value: Char) {
        updateUiState {
            it.copy(
                expressionValue = buildAnnotatedString {
                    append(it.expressionValue)
                    append(value)
                },
                isOperator = false
            )
        }

        if (_uiState.value.hasOperator) {
            updateCalculation()
        }
    }

    private fun updateCalculation() {
        try {
            val result = calculator.calculate(_uiState.value.expressionValue.text)
            updateUiState { it.copy(resultValue = result) }
        } catch (e: Exception) {
            // 계산 중 에러가 발생하면 무시
        }
    }

    private fun calculateFinalResult() {
        val expression = _uiState.value.expressionValue.text
        if (expression.isEmpty() || !_uiState.value.hasOperator) return

        try {
            val result = calculator.calculate(expression)
            
            // 계산 결과 저장
            saveCalculationHistory(expression, result)
            
            // UI 업데이트
            updateUiState {
                it.copy(
                    expressionValue = AnnotatedString(result),
                    resultValue = "",
                    isOperator = false,
                    hasOperator = false
                )
            }
        } catch (e: Exception) {
            updateUiState { it.copy(isError = true) }
        }
    }

    private fun saveCalculationHistory(expression: String, result: String) {
        viewModelScope.launch {
            saveHistoryUseCase(
                CalculatorHistory(
                    expression = expression,
                    result = result
                )
            )
        }
    }

    private fun clearInput() {
        _uiState.value = CalculatorUiState()
    }
    
    fun toggleHistoryVisibility() {
        updateUiState { it.copy(isHistoryVisible = !it.isHistoryVisible) }
    }
    
    fun clearHistories() {
        viewModelScope.launch {
            deleteAllHistoriesUseCase()
        }
    }

    private fun updateUiState(update: (CalculatorUiState) -> CalculatorUiState) {
        _uiState.update(update)
    }

    private fun isOperator(value: Char): Boolean {
        return Operator.entries.any { it.value == value }
    }
}

private class Calculator {
    fun calculate(expression: String): String {
        // 공백 제거 및 정리
        val cleanExpression = expression.replace(" ", "")
        if (cleanExpression.isEmpty()) return "0"

        var result = 0.0
        var currentNumber = ""
        var operator = '+'
        
        for (i in cleanExpression.indices) {
            val c = cleanExpression[i]
            
            if (c.isDigit() || c == '.') {
                currentNumber += c
            } else if (isCalculationOperator(c)) {
                // 누적된 숫자 처리
                result = applyOperation(result, currentNumber, operator)
                currentNumber = ""
                operator = c
            }
        }
        
        // 마지막 숫자 처리
        if (currentNumber.isNotEmpty()) {
            result = applyOperation(result, currentNumber, operator)
        }
        
        // 정수 결과는 소수점 제거
        return formatResult(result)
    }

    private fun isCalculationOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '×' || c == '÷'
    }

    private fun applyOperation(result: Double, numberStr: String, operator: Char): Double {
        if (numberStr.isEmpty()) return result
        
        val number = numberStr.toDoubleOrNull() ?: 0.0
        
        return when (operator) {
            '+' -> result + number
            '-' -> result - number
            '×' -> result * number
            '÷' -> {
                if (number == 0.0) throw ArithmeticException("나누기 0 에러")
                result / number
            }
            else -> result
        }
    }

    private fun formatResult(result: Double): String {
        return if (result == result.toInt().toDouble()) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}

enum class Operator(val value: Char) {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('×'),
    DIVIDE('÷'),
    PARENTHESIS('('),
    PERCENT('%'),
    EQUAL('='),
    CLEAR('C')
}

data class CalculatorUiState(
    val expressionValue: AnnotatedString = AnnotatedString(""),
    val resultValue: String = "",
    val isOverLength: Boolean = false,
    val isZero: Boolean = false,
    val isSameOperator: Boolean = false,
    val isError: Boolean = false,
    val isHistoryVisible: Boolean = false,
    val isOperator: Boolean = false,
    val hasOperator: Boolean = false
)