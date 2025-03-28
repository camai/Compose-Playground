package com.jg.composeplayground.diary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jg.composeplayground.common.ui.component.keypad.NumberKeypad
import com.jg.composeplayground.diary.viewmodel.PasswordState
import com.jg.composeplayground.diary.viewmodel.SecretKeypadViewModel
import com.jg.composeplayground.feature.diary.R

@Composable
internal fun SecretKeypadRoute(
    viewModel: SecretKeypadViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onBackPress: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isDone) {
        if (uiState.isDone) {
            viewModel.resetState()
            onSuccess()
        }
    }

    SecretKeypadScreen(
        length = uiState.inputLength,
        passwordState = uiState.passwordState,
        errorMessage = uiState.errorMessage,
        onNumberClick = viewModel.addNumber,
        onClearClick = viewModel.clear,
        onDoneClick = viewModel.done,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SecretKeypadScreen(
    length: Int,
    passwordState: PasswordState,
    errorMessage: String?,
    onNumberClick: (Char) -> Unit,
    onClearClick: () -> Unit,
    onDoneClick: () -> Unit,
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "비밀번호 입력 해주세요",
                    style = MaterialTheme.typography.headlineMedium
                )

                Box {
                    SecretImageBoxes(
                        modifier = Modifier
                            .fillMaxWidth(),
                        inputLength = length,
                        passwordState = passwordState
                    )
                }

                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                NumberKeypad(
                    modifier = Modifier
                        .fillMaxSize(),
                    onNumberClick = onNumberClick,
                    onClearClick = onClearClick,
                    onDoneClick = onDoneClick
                )
            }
        }
    }
}

const val MAX_LENGTH = 4

@Composable
private fun SecretImageBoxes(
    modifier: Modifier = Modifier,
    inputLength: Int,
    passwordState: PasswordState
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (passwordState) {
            PasswordState.INIT -> {
                repeat(MAX_LENGTH) {
                    PasswordImageBox(
                        color = Color.Black
                    )
                }
            }

            PasswordState.INPUT -> {
                repeat(MAX_LENGTH) { index ->
                    if (index < inputLength) {
                        PasswordImageBox(color = Color.Blue)
                    } else {
                        PasswordImageBox(color = Color.Black)
                    }
                }
            }

            PasswordState.Warning -> {
                repeat(MAX_LENGTH) { index ->
                    if (index < inputLength) {
                        PasswordImageBox(color = Color.Red)
                    } else {
                        PasswordImageBox(color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordImageBox(
    color: Color
) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Image(
            painterResource(id = R.drawable.ic_password),
            contentDescription = "Password Normal Icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}