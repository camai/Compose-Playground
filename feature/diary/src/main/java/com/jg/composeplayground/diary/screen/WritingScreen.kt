package com.jg.composeplayground.diary.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.model.enums.DiaryWritingType
import com.jg.composeplayground.model.navigation.DiaryWritingArgs
import com.jg.composeplayground.diary.viewmodel.WritingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WritingRoute(
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
fun WritingScreen(
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

