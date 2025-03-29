package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jg.composeplayground.model.data.CalculatorHistory

@Composable
internal fun HistoryScreen(
    modifier: Modifier,
    histories: List<CalculatorHistory>,
    onClearClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn {
            items(histories.size) { history ->
                HistoryItem(
                    expressionValue = histories[history].expression,
                    resultValue = histories[history].result
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            onClick = onClearClick,
        ) {
            Text(
                modifier = Modifier
                    .background(color = Color.Green),
                text = "계산 기록 삭제",
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun HistoryItem(
    expressionValue: String,
    resultValue: String
) {
    Column (
       modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = expressionValue,
            textAlign = TextAlign.End,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )

        Text(
            text = "= $resultValue",
            textAlign = TextAlign.End,
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Green
        )
    }
}