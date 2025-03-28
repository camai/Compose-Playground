package com.jg.composeplayground.calculator.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.calculator.viewmodel.CalculatorViewModel

@Composable
internal fun CalculatorRoute (
    viewModel: CalculatorViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {

    CalculatorScreen(
        inputValue = "12",
        resultValue = "12",
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalculatorScreen (
    inputValue: String,
    resultValue: String,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
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
                CalculatingScreen(
                    modifier = Modifier
                        .weight(1f),
                    inputValue = inputValue,
                    resultValue = resultValue
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .background(Color.Black)
                )


            }
        }
    }
}


@Composable
private fun CalculatingScreen(
    modifier: Modifier = Modifier,
    inputValue: String,
    resultValue: String
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            text = inputValue,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = resultValue,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray
        )
    }
}