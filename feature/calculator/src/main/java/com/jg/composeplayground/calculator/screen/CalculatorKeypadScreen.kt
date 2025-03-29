package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.jg.composeplayground.calculator.R
import com.jg.composeplayground.common.ui.component.keypad.RippleCharButton
import com.jg.composeplayground.common.ui.component.keypad.RippleImageButton
import com.jg.composeplayground.common.ui.component.keypad.RippleTextButton

@Composable
internal fun CalculatorKeypadScreen(
    modifier: Modifier = Modifier,
    onNumberClick: (Char) -> Unit,
    onDoneClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val keys = remember {
        getCalKeys()
    }

    Box(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),  // 너비만 꽉 차게
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),  // 버튼 간 간격 증가
            content = {
                items(keys) { key ->
                    when (key) {
                        CalKey.HISTORY -> {
                            RippleImageButton(
                                modifier = Modifier.aspectRatio(1.8f),  // 버튼 비율 조정
                                bgColor = Color(0xFFEEEEEE),
                                rippleColor = Color(0xFFDDDDDD),
                                onPressed = {
                                    onHistoryClick()
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                },
                                imageId = R.drawable.ic_history
                            )
                        }
                        CalKey.EQUAL -> {
                            RippleTextButton(
                                modifier = Modifier.aspectRatio(1.8f),  // 버튼 비율 조정
                                bgColor = Color(0xFF8BC34A),
                                rippleColor = Color(0xFF7CB342),
                                textColor = Color.White,
                                onPressed = {
                                    onDoneClick()
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                },
                                textStyle = MaterialTheme.typography.headlineMedium,
                                value = "="
                            )
                        }
                        else -> {
                            val value = key.value
                            
                            val bgColor = when (key) {
                                CalKey.CLEAR -> Color(0xFFEEEEEE)
                                CalKey.PARENTHESIS -> Color(0xFFEEEEEE)
                                CalKey.PERCENT -> Color(0xFFEEEEEE)
                                CalKey.DIVIDE -> Color(0xFFEEEEEE)
                                CalKey.MULTIPLY -> Color(0xFFEEEEEE)
                                CalKey.MINUS -> Color(0xFFEEEEEE)
                                CalKey.PLUS -> Color(0xFFEEEEEE)
                                else -> Color(0xFFEEEEEE) // 숫자와 소수점은 기본 배경색
                            }

                            RippleCharButton(
                                modifier = Modifier.aspectRatio(1.8f),  // 버튼 비율 조정
                                bgColor = bgColor,
                                rippleColor = Color(0xFFDDDDDD),
                                textColor = key.color,
                                onPressed = {
                                    onNumberClick(value)
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                },
                                textStyle = MaterialTheme.typography.headlineMedium,
                                value = value
                            )
                        }
                    }
                }
            }
        )
    }
}

private fun getCalKeys(): List<CalKey> {
    return listOf(
        CalKey.CLEAR,
        CalKey.PARENTHESIS,
        CalKey.PERCENT,
        CalKey.DIVIDE,
        CalKey.NUM_7,
        CalKey.NUM_8,
        CalKey.NUM_9,
        CalKey.MULTIPLY,
        CalKey.NUM_4,
        CalKey.NUM_5,
        CalKey.NUM_6,
        CalKey.MINUS,
        CalKey.NUM_1,
        CalKey.NUM_2,
        CalKey.NUM_3,
        CalKey.PLUS,
        CalKey.HISTORY,
        CalKey.NUM_0,
        CalKey.DOT,
        CalKey.EQUAL
    )
}

private enum class CalKey(val value: Char, val color: Color) {
    CLEAR(value = 'C', color = Color(0xFFE53935)), // 빨간색
    PARENTHESIS(value = '(', color = Color(0xFF558B2F)), // 녹색
    PERCENT(value = '%', color = Color(0xFF558B2F)), // 녹색
    DIVIDE(value = '÷', color = Color(0xFF558B2F)), // 녹색
    NUM_7(value = '7', color = Color.Black),
    NUM_8(value = '8', color = Color.Black),
    NUM_9(value = '9', color = Color.Black),
    MULTIPLY(value = '×', color = Color(0xFF558B2F)), // 녹색
    NUM_4(value = '4', color = Color.Black),
    NUM_5(value = '5', color = Color.Black),
    NUM_6(value = '6', color = Color.Black),
    MINUS(value = '-', color = Color(0xFF558B2F)), // 녹색
    NUM_1(value = '1', color = Color.Black),
    NUM_2(value = '2', color = Color.Black),
    NUM_3(value = '3', color = Color.Black),
    PLUS(value = '+', color = Color(0xFF558B2F)), // 녹색
    HISTORY(value = 'H', color = Color.DarkGray), // 시계 아이콘으로 사용
    NUM_0(value = '0', color = Color.Black),
    DOT(value = '.', color = Color.Black),
    EQUAL(value = '=', color = Color.White)
}