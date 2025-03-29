package com.jg.composeplayground.diary.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.diary.viewmodel.WritingViewModel
import com.jg.composeplayground.model.enums.DiaryWritingType
import com.jg.composeplayground.model.navigation.DiaryWritingArgs


@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun WritingRoute(
    onNavigateBack: () -> Unit,
    argument: DiaryWritingArgs,
    viewModel: WritingViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = argument) {
        viewModel.initDiary(argument)
    }

    val uiState by viewModel.uiState.collectAsState()

    // 저장 완료 후 자동으로 뒤로가기
    LaunchedEffect(key1 = uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    WritingScreen(
        content = uiState.content,
        diaryWritingType = uiState.diaryWritingType,
        onContentChange = viewModel::onContentChange,
        onSaveClick = viewModel::onSaveClick,
        onNavigateBack = onNavigateBack
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WritingScreen(
    content: String,
    diaryWritingType: DiaryWritingType,
    onContentChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    // 모드에 따른 화면 제목 설정
    val screenTitle = when (diaryWritingType) {
        DiaryWritingType.WRITING -> "일기 작성"
        DiaryWritingType.EDIT_POST -> "일기 수정"
    }

    // 모드에 따른 버튼 텍스트 설정
    val buttonText = when (diaryWritingType) {
        DiaryWritingType.WRITING -> "작성 완료"
        DiaryWritingType.EDIT_POST -> "수정 완료"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // 글쓰기 영역
            OutlinedTextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .verticalScroll(rememberScrollState()),
                placeholder = { Text("일기를 작성해주세요...") }
            )

            // 저장 버튼
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(buttonText)
            }
        }
    }
}

