package com.jg.diary.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.core.model.enums.DiaryWritingType
import com.jg.composeplayground.core.model.navigation.DiaryWritingArgs
import com.jg.diary.viewmodel.DiaryViewModel

@Composable
fun DiaryRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onNavigateToWriting: (DiaryWritingArgs) -> Unit
) {
    DiaryScreen(
        onBackPress = onBackPress,
        onNavigateToWriting = onNavigateToWriting
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    onBackPress: () -> Unit,
    onNavigateToWriting: (DiaryWritingArgs) -> Unit
) {
    // 스크롤 동작을 위한 설정
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            // 스크롤 동작 연결
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
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
            // 기존 "글쓰기" 버튼을 LazyColumn 헤더에 포함
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // 헤더로 글쓰기 버튼 추가
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, end = 24.dp)
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                onNavigateToWriting(
                                    DiaryWritingArgs(
                                        diaryId = null,
                                        diaryWritingType = DiaryWritingType.WRITING,
                                        content = "",
                                        date = ""
                                    )
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "글쓰기"
                            )
                        }
                    }
                }

                // 일기 아이템들
                items(10) { index ->
                    DiaryItem(
                        date = "2023년 10월 ${index + 1}일",
                        content = "오늘의 일기 내용입니다. 이것은 첫 번째 줄이고, 추가 내용은 생략됩니다.",
                        onClick = {
                            onNavigateToWriting(
                                DiaryWritingArgs(
                                    diaryId = index,
                                    diaryWritingType = DiaryWritingType.EDIT_POST,
                                    content = "오늘의 일기 내용입니다. 이것은 첫 번째 줄이고, 추가 내용은 생략됩니다.",
                                    date = "2023-10-${index + 1} 12:00:00"
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DiaryItem(
    date: String,
    content: String,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 날짜 표시 (상단 오른쪽)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 날짜 아이콘
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "날짜",
                    tint = MaterialTheme.colorScheme.primary
                )

                // 날짜 텍스트
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 구분선
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(vertical = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(1.dp)
                    )
            )

            // 내용 미리보기 (첫 줄만)
            Text(
                text = content.split("\n").first(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}