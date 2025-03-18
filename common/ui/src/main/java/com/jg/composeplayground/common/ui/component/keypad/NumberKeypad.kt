package com.jg.composeplayground.common.ui.component.keypad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jg.composeplayground.common.R

@Composable
fun NumberKeypad(
    onNumberClick: (Char) -> Unit,
    onClearClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val keys by remember {
        derivedStateOf {
            getPinKeys()
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(24.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            items(keys.size) { index ->
                when (keys[index]) {
                    NumKey.COMPLETE -> {
                        RippleImageButton(
                            bgColor = Color.White,
                            rippleColor = Color(0xFFF7F8FB),
                            onPressed = {
                                onDoneClick()
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            imageId = R.drawable.ic_done
                        )
                    }

                    NumKey.DELETE -> {
                        RippleImageButton(
                            bgColor = Color.White,
                            rippleColor = Color(0xFFF7F8FB),
                            onPressed = {
                                onClearClick()
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            imageId = R.drawable.ic_delete
                        )
                    }

                    else -> {
                        val value = keys[index].value

                        RippleCharButton(
                            bgColor = Color.White,
                            rippleColor = Color(0xFFF7F8FB),
                            onPressed = {
                                onNumberClick(value)
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            textStyle = TextStyle.Default,
                            value = value
                        )
                    }
                }
            }
        }
    )
}

private fun getPinKeys() = numKeys
    .toMutableList()
    .apply {
        add(index = 9, element = NumKey.DELETE)
        add(element = NumKey.COMPLETE)
    }.toList()

private val numKeys = listOf<NumKey>(
    NumKey.NUM_1,
    NumKey.NUM_2,
    NumKey.NUM_3,
    NumKey.NUM_4,
    NumKey.NUM_5,
    NumKey.NUM_6,
    NumKey.NUM_7,
    NumKey.NUM_8,
    NumKey.NUM_9,
    NumKey.NUM_0,
)

private enum class NumKey(val value: Char) {
    NUM_1(value = '1'),
    NUM_2(value = '2'),
    NUM_3(value = '3'),
    NUM_4(value = '4'),
    NUM_5(value = '5'),
    NUM_6(value = '6'),
    NUM_7(value = '7'),
    NUM_8(value = '8'),
    NUM_9(value = '9'),
    NUM_0(value = '0'),
    DELETE(value = 'D'),
    COMPLETE(value = 'C')
}

@Preview(showBackground = true)
@Composable
fun NumberKeypadPreview() {
    NumberKeypad(
        onNumberClick = {},
        onClearClick = {},
        onDoneClick = {}
    )
}