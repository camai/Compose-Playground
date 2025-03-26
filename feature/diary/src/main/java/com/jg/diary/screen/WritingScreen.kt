package com.jg.diary.screen

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
import com.jg.composeplayground.core.model.enums.DiaryWritingType
import com.jg.composeplayground.core.model.navigation.DiaryWritingArgs
import com.jg.diary.viewmodel.WritingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WritingRoute(
    onNavigateBack: () -> Unit,
    argument: DiaryWritingArgs,
    viewModel: WritingViewModel = hiltViewModel()
) {
    println("!! argument=$argument")
    val uiState by viewModel.uiState.collectAsState()

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (diaryWritingType == DiaryWritingType.EDIT_POST) "일기 수정" else "일기 작성") },
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
                Text(if (diaryWritingType == DiaryWritingType.EDIT_POST) "수정 완료" else "작성 완료")
            }
        }
    }
}

