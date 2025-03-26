package com.jg.composeplayground.diary.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.domain.model.Diary
import com.jg.composeplayground.model.enums.DiaryWritingType
import com.jg.composeplayground.model.navigation.DiaryWritingArgs
import com.jg.composeplayground.diary.viewmodel.DiaryViewModel

@Composable
fun DiaryRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onNavigateToWriting: (DiaryWritingArgs) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DiaryScreen(
        diaries = uiState.diaries,
        formatDate = viewModel::formatDate,
        onDeleteDiary = viewModel::deleteDiary,
        onBackPress = onBackPress,
        onNavigateToWriting = onNavigateToWriting
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    diaries: List<Diary>,
    formatDate: (java.time.LocalDateTime?) -> String,
    onDeleteDiary: (Int) -> Unit,
    onBackPress: () -> Unit,
    onNavigateToWriting: (DiaryWritingArgs) -> Unit
) {
    // 스크롤 동작을 위한 설정
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // 삭제 확인 다이얼로그 상태
    var showDeleteDialog by remember { mutableStateOf(false) }
    var diaryToDelete by remember { mutableStateOf<Diary?>(null) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            // 스크롤 동작 연결
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("나의 일기장") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: 휴지통 기능 구현 */ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "휴지통 목록"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                // 스크롤 동작 적용
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToWriting(
                        DiaryWritingArgs(
                            diaryWritingType = DiaryWritingType.WRITING
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "새 일기 작성")
            }
        }
    ) { innerPadding ->
        // 전체 내용을 하나의 Column에 넣어 스크롤되도록 함
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            // 여기서 verticalScroll을 적용하면 LazyColumn과 충돌
            // 대신 LazyColumn을 전체 화면을 채우도록 함
        ) {
            // 일기 목록 표시
            if (diaries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("작성된 일기가 없습니다. 새 일기를 작성해보세요!")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(diaries) { diary ->
                        DiaryItem(
                            diary = diary,
                            formattedDate = formatDate(diary.createdAt),
                            onClick = {
                                onNavigateToWriting(
                                    DiaryWritingArgs(
                                        diaryId = diary.id,
                                        diaryWritingType = DiaryWritingType.EDIT_POST,
                                        content = diary.content,
                                        date = formatDate(diary.createdAt)
                                    )
                                )
                            },
                            onLongClick = {
                                diaryToDelete = diary
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    // 삭제 확인 다이얼로그
    if (showDeleteDialog && diaryToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("일기 삭제") },
            text = { Text("이 일기를 삭제하시겠습니까?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        diaryToDelete?.let { onDeleteDiary(it.id) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("취소")
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DiaryItem(
    diary: Diary,
    formattedDate: String,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Modifier.weight(1f)
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "편집",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .clickable { onClick() }
                )
            }
            Text(
                text = diary.content,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}