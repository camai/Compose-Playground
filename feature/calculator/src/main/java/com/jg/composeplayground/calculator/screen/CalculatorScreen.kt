package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jg.composeplayground.calculator.viewmodel.CalculatorViewModel
import com.jg.composeplayground.model.data.CalculatorHistory
import androidx.compose.ui.text.AnnotatedString

/**
 *  기본 계산 처리만 하는걸로 간단하게 구현
 *  % () 을 제외한 나머지 연산만
 */
@Composable
internal fun CalculatorRoute (
    viewModel: CalculatorViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val histories by viewModel.histories.collectAsStateWithLifecycle()

    CalculatorScreen(
        inputValue = uiState.expressionValue,
        resultValue = uiState.resultValue,
        isHistoryVisible = uiState.isHistoryVisible,
        histories = histories,
        onNumberClick = viewModel::inputValue,
        onDoneClick = { viewModel.inputValue('=') },
        onHistoryClick = viewModel::toggleHistoryVisibility,
        onHistoryDismiss = viewModel::clearHistories,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorScreen (
    inputValue: AnnotatedString,
    resultValue: String,
    isHistoryVisible: Boolean,
    histories: List<CalculatorHistory>,
    onNumberClick: (Char) -> Unit,
    onDoneClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onHistoryDismiss: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(if (isHistoryVisible) "계산기 기록" else "계산기") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // 표현식 영역(상단에 위치)
                ExpressionScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    inputValue = inputValue,
                    resultValue = resultValue
                )

                // 키패드 또는 히스토리 영역(하단에 위치)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (isHistoryVisible) {
                        HistoryScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            onClearClick = onHistoryDismiss,
                            histories = histories
                        )
                    } else {
                        CalculatorKeypadScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            onNumberClick = onNumberClick,
                            onDoneClick = onDoneClick,
                            onHistoryClick = onHistoryClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpressionScreen(
    modifier: Modifier = Modifier,
    inputValue: AnnotatedString,
    resultValue: String
) {
    Column(
        modifier = modifier
    ) {
        // 계산식 표시 영역
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            // 입력값 영역 - 상단에 위치
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = inputValue,
                    textAlign = TextAlign.End,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
            
            // 중간 여백 영역
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // 결과값 영역 - 구분선 바로 위에 위치
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = resultValue,
                    textAlign = TextAlign.End,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
        
        // 구분선
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Preview
@Composable
private fun CalculatorPreviewScreen() {
    CalculatorScreen(
        inputValue = AnnotatedString("23"),
        resultValue = "23",
        isHistoryVisible = false,
        histories = emptyList(),
        onNumberClick = {},
        onDoneClick = {},
        onHistoryClick = {},
        onHistoryDismiss = {},
        onBackPress = {}
    )
}