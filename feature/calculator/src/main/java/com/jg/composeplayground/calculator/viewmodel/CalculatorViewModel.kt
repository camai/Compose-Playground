package com.jg.composeplayground.calculator.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
): ViewModel() {
    /**
     * 값을 char 로 입력 받는다.
     * 숫자 또는 연산자 여부를 구분한다.
     * 숫자와 연산자 표기를 space 영역으로 구분한다.
     * 숫자와 연산자의 표기된 값을 표기하면서, 결과값도 같이 표기해준다.
     * 던 처리일 경우 기록을 저장한다. (연산된 값과 결과값)
     */

    private val _uiState = MutableStateFlow(ExpressionData.EMPTY)
    val uiState = _uiState.asStateFlow()

    private var isOperator = false
    private var hasOperator = false

    fun inputValue(value: Char) {
        /**
         * 입력된 값에 왔을때, 현재 입력된 값이 비어 있지 않는데 15자리 이상 경우 예외 처리
         * 입력된 값이 비어 있는데 0일 때 예외처리
         * 입력 된 값이 연산자 인지 아닌지 판단
         * 연산자일 경우
         *  바포 앞에 연산자 일 경우 연산자를 교체
         *  동일한 연산자일 경우는 무시
         *  연산자가 없을 경우는 한칸 띄우고 연산자 추가 (연산자일 경우 SpannableStringBuilder 로 색상 교체)
         * 숫자 일 경우는 그냥 숫자로 넣어준다.
         */

        if (_uiState.value.expressionValue.text.isNotEmpty() && _uiState.value.expressionValue.text.length >= 15) {
            _uiState.update {
                it.copy(
                    isOverLength = true
                )
            }
        } else if (_uiState.value.expressionValue.text.isEmpty() && value == '0') {
            _uiState.update {
                it.copy(
                    isZero = true
                )
            }
        } else {
            if (Operator.entries.any{ it.value == value }) {
                if (Operator.CLEAR.value == value) {
                    clear()
                    return
                }

                var currentExpression = _uiState.value.expressionValue.text
                when {
                    isOperator -> {
                        currentExpression = currentExpression.dropLast(1) + value
                    }
                    hasOperator -> {
                        _uiState.update {
                            it.copy(
                                isSameOperator = true
                            )
                        }
                        return
                    }
                    else -> {
                        currentExpression = "$currentExpression $value"
                    }
                }

                val annotatedString = buildAnnotatedString {
                    append(currentExpression)
                    addStyle(
                        style = SpanStyle(color = Color.Green),
                        start = currentExpression.length - 1,
                        end = currentExpression.length
                    )
                }

                _uiState.update {
                    it.copy(expressionValue = annotatedString)
                }

                isOperator = true
                hasOperator = true
            } else {
                inputNumber(value)
            }
        }
    }

    private fun plusValue() {
        _uiState.update {
            it.copy(
                expressionValue = buildAnnotatedString {
                    append(it.expressionValue)
                    append(Operator.PLUS.value)
                }
            )
        }
    }

    private fun inputNumber(value: Char) {
        _uiState.update {
            it.copy(
                expressionValue = buildAnnotatedString {
                    append(it.expressionValue)
                    append(value)
                }
            )
        }
    }

    private fun clear() {
        _uiState.value = ExpressionData.EMPTY
    }
}

enum class Operator(val value: Char) {
    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    PARENTHESIS('('),
    PERCENT('%'),
    CLEAR('C')
}

data class ExpressionData(
    val expressionValue: AnnotatedString,
    val resultValue: String,
    val isOverLength: Boolean,
    val isZero: Boolean,
    val isSameOperator: Boolean
) {
    companion object {
        val EMPTY = ExpressionData(
            expressionValue = AnnotatedString(""),
            resultValue = "",
            isOverLength = false,
            isZero = false,
            isSameOperator = false)
    }
}