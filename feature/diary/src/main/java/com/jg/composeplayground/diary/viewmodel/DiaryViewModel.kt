package com.jg.composeplayground.diary.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.composeplayground.model.data.Diary
import com.jg.composeplayground.domain.usecase.diary.DeleteDiaryUseCase
import com.jg.composeplayground.domain.usecase.diary.GetAllDiariesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getAllDiariesUseCase: GetAllDiariesUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(DiaryUiState())
    val uiState: StateFlow<DiaryUiState> = _uiState.asStateFlow()

    init {
        loadDiaries()
    }

    private fun loadDiaries() {
        viewModelScope.launch {
            getAllDiariesUseCase().collect { diaries ->
                _uiState.update { currentState ->
                    currentState.copy(diaries = diaries)
                }
            }
        }
    }

    fun deleteDiary(diaryId: Int) {
        viewModelScope.launch {
            deleteDiaryUseCase(diaryId)
            // 삭제 후 UI에서 즉시 반영 (필요한 경우)
            _uiState.update { currentState ->
                currentState.copy(
                    diaries = currentState.diaries.filter { it.id != diaryId }
                )
            }
        }
    }

    // 날짜 포맷 변환 유틸리티 함수
    fun formatDate(dateTime: LocalDateTime?): String {
        if (dateTime == null) return ""
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"))
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
}

data class DiaryUiState(
    val diaries: List<Diary> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)