package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jg.composeplayground.model.data.CalculatorHistory

@Composable
internal fun HistoryScreen(
    modifier: Modifier,
    histories: List<CalculatorHistory>,
    onClearClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
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
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp, horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(Color.Green),
            onClick = onClearClick,
        ) {
            Text(
                text = "계산 기록 삭제",
                textAlign = TextAlign.Center,
                color = Color.Black,
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
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = expressionValue,
            textAlign = TextAlign.End,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = resultValue,
            textAlign = TextAlign.End,
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Green
        )
    }
}

@Preview
@Composable
private fun PreviewHistoryItems() {
    HistoryScreen(
        modifier = Modifier,
        histories = listOf(
            CalculatorHistory(expression = "23 + 34", result = "57")
        ),
        onClearClick = {}
    )
}