package com.jg.composeplayground.lotto.screen

import android.widget.NumberPicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.lotto.utils.NumberCircle
import com.jg.composeplayground.lotto.viewmodel.LottoViewModel

@Composable
internal fun LottoRoute(
    viewModel: LottoViewModel = hiltViewModel()
) {
    LottoScreen()
}

@Composable
private fun LottoScreen(

) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottoNumberPicker(
                    onValueChange = { newValue ->

                    },
                    range = 1..45
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(56.dp)
                            .padding(end = 8.dp)
                    ) {
                        Text("번호 추가하기")
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .height(56.dp)
                            .padding(start = 8.dp)
                    ) {
                        Text("초기화")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = true
                ) {
                    LottoNumbers(
                        numbers = listOf(1, 2, 3, 4, 5, 6)
                    )
                }
            }
        }
    }
}

@Composable
private fun LottoNumberPicker(
    value: Int = 1,
    onValueChange: (Int) -> Unit,
    range: IntRange = 0..100
) {
    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                minValue = range.first
                maxValue = range.last
                this.value = value
                setOnValueChangedListener { _, _, newValue ->
                    onValueChange(newValue)
                }
            }
        },
        update = { view ->
            view.value = value
        }
    )
}

@Composable
private fun LottoNumbers(
    numbers: List<Int>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        numbers.forEach { number ->
            NumberCircle(
                number = number,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}