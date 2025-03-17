package com.jg.composeplayground.lotto.screen

import android.widget.NumberPicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jg.composeplayground.lotto.utils.NumberCircle
import com.jg.composeplayground.lotto.viewmodel.LottoViewModel
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
internal fun LottoRoute(
    viewModel: LottoViewModel = hiltViewModel(),
    onBackPress: () -> Unit = { }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LottoScreen(
        lottoState = uiState,
        onAddNumber = viewModel.updateLottoNumber,
        onRandomLottoNumbers = viewModel.randomLottoNumbers,
        onClearLottoNumbers = viewModel.clearLottoNumbers,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LottoScreen(
    lottoState: LottoViewModel.LottoState,
    onAddNumber: (number: Int) -> Unit = {},
    onRandomLottoNumbers: () -> Unit = {},
    onClearLottoNumbers: () -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor =  MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var selectNumber by remember { mutableStateOf(1) }

                LottoNumberPicker(
                    value = selectNumber,
                    onValueChange = { newValue ->
                        selectNumber = newValue
                    },
                    range = 1..45
                )

                Spacer(modifier = Modifier.height(16.dp))

                LottoActionButtons(
                    onAddNumber = onAddNumber,
                    onClearNumbers = onClearLottoNumbers,
                    selectedNumber = selectNumber,
                    isAddEnabled = lottoState.numbers.size < 6 && !lottoState.numbers.contains(
                        selectNumber
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(visible = lottoState.numbers.isNotEmpty()) {
                    LottoNumbers(
                        numbers = lottoState.numbers
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onRandomLottoNumbers()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("자동 생성 시작")
                }

                Text(
                    text = "선택한 번호: ${lottoState.numbers.size}/6",
                    style = MaterialTheme.typography.bodyMedium
                )

                if (lottoState.isComplete) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "번호 선택 완료!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
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
private fun LottoActionButtons(
    onAddNumber: (Int) -> Unit,
    onClearNumbers: () -> Unit,
    selectedNumber: Int,
    isAddEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onAddNumber(selectedNumber) },
            modifier = Modifier
                .height(56.dp)
                .padding(end = 8.dp),
            enabled = isAddEnabled
        ) {
            Text("번호 추가하기")
        }

        Button(
            onClick = { onClearNumbers() },
            modifier = Modifier
                .height(56.dp)
                .padding(start = 8.dp)
        ) {
            Text("초기화")
        }
    }
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