package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jg.composeplayground.common.ui.component.keypad.RippleCharButton
import com.jg.composeplayground.common.ui.component.keypad.RippleTextButton

@Composable
fun CalculatorKeyPadScreen(
    modifier: Modifier = Modifier,
    onNumberClick: (Char) -> Unit,
    onClearClick: () -> Unit,
    onDoneClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val keys by remember {
        derivedStateOf {
            getCalKeys()
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(24.dp),
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(keys.size) { index ->
                when (keys[index]) {
                    CalKey.CLEAR -> {

                    }
                    CalKey.PARENTHESIS -> {
                        val value = keys[index].value

                        RippleTextButton(
                            bgColor = Color.White,
                            rippleColor = Color(0xFFF7F8FB),
                            onPressed = {
                                onNumberClick(value)
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            },
                            textStyle = MaterialTheme.typography.headlineMedium,
                            value = "()"
                        )
                    }
                    CalKey.HISTORY -> {

                    }
                    CalKey.EQUAL -> {

                    }
                    else -> {

                    }
                }
            }
        }
    )
}


private fun getCalKeys() = calKeys
    .toMutableList()
    .apply {
        add(element = CalKey.CLEAR)
        add(element = CalKey.PARENTHESIS)
        add(element = CalKey.PERCENT)
        add(element = CalKey.DIVIDE)
        add(element = CalKey.NUM_7)
        add(element = CalKey.NUM_8)
        add(element = CalKey.NUM_9)
        add(element = CalKey.MULTIPLY)
        add(element = CalKey.NUM_4)
        add(element = CalKey.NUM_5)
        add(element = CalKey.NUM_6)
        add(element = CalKey.MINUS)
        add(element = CalKey.NUM_1)
        add(element = CalKey.NUM_2)
        add(element = CalKey.NUM_3)
        add(element = CalKey.PLUS)
        add(element = CalKey.HISTORY)
        add(element = CalKey.NUM_0)
        add(element = CalKey.DOT)
        add(element = CalKey.EQUAL)
    }

private val calKeys = listOf<CalKey>(
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

private enum class CalKey(val value: Char, val color: Color) {
    CLEAR(value = 'C', color = Color.Red),
    PARENTHESIS(value = '(', color = Color.Green),
    PERCENT(value = '%', color = Color.Green),
    DIVIDE(value = '÷', color = Color.Green),
    NUM_7(value = '7', color = Color.Black),
    NUM_8(value = '8', color = Color.Black),
    NUM_9(value = '9', color = Color.Black),
    MULTIPLY(value = '×', color = Color.Green),
    NUM_4(value = '4', color = Color.Black),
    NUM_5(value = '5', color = Color.Black),
    NUM_6(value = '6', color = Color.Black),
    MINUS(value = '-', color = Color.Green),
    NUM_1(value = '1', color = Color.Black),
    NUM_2(value = '2', color = Color.Black),
    NUM_3(value = '3', color = Color.Black),
    PLUS(value = '+', color = Color.Green),
    HISTORY(value = 'H', color = Color.Gray),
    NUM_0(value = '0', color = Color.Black),
    DOT(value = '.', color = Color.Black),
    EQUAL(value = '=', color = Color.White)
}