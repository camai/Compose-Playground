package com.jg.diary.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.diary.viewmodel.DiaryViewModel

@Composable
fun DiaryRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    DiaryScreen(
        onBackPress = onBackPress,
    )
}

@Composable
fun DiaryScreen(
    onBackPress: () -> Unit,
) {
}