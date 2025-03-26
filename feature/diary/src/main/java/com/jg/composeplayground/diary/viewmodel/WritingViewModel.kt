package com.jg.composeplayground.diary.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.composeplayground.model.enums.DiaryWritingType
import com.jg.composeplayground.model.navigation.DiaryWritingArgs
import com.jg.composeplayground.domain.usecase.diary.GetDiaryByIdUseCase
import com.jg.composeplayground.domain.usecase.diary.SaveDiaryUseCase
import com.jg.composeplayground.domain.usecase.diary.UpdateDiaryUseCase
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
class WritingViewModel @Inject constructor(
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase,
    private val getDiaryByIdUseCase: GetDiaryByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WritingUiState())
    val uiState: StateFlow<WritingUiState> = _uiState.asStateFlow()

    fun onContentChange(content: String) {
        _uiState.update { current ->
            current.copy(content = content)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveClick() {
        val currentDateTime = LocalDateTime.now()
        val formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        
        // 일기 ID 여부에 따라 저장 또는 업데이트
        if (_uiState.value.diaryId != null) {
            updateDiary()
        } else {
            saveDiary()
        }

        // 저장 완료 상태 업데이트
        _uiState.update { current ->
            current.copy(
                date = formattedDateTime,
                isSaved = true
            )
        }
    }

    fun initDiary(data: DiaryWritingArgs) {
        _uiState.update { current ->
            current.copy(
                content = data.content,
                date = data.date,
                diaryWritingType = data.diaryWritingType,
                diaryId = data.diaryId
            )
        }
        
        when (data.diaryWritingType) {
            DiaryWritingType.WRITING -> {
                // 새 일기 작성 시 초기화 로직이 필요하면 여기에 추가
            }
            DiaryWritingType.EDIT_POST -> {
                // 기존 일기 편집 모드인 경우 해당 ID의 일기 내용을 가져옴
                data.diaryId?.let { 
                    if (data.content.isEmpty()) {
                        // API로부터 내용을 가져와야 하는 경우
                        getPostContentToEdit(it)
                    }
                }
            }
        }
    }

    private fun getPostContentToEdit(diaryId: Int) {
        viewModelScope.launch {
            val diary = getDiaryByIdUseCase(diaryId)
            diary?.let {
                _uiState.update { current ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        current.copy(
                            content = it.content,
                            date = it.createdAt?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) ?: ""
                        )
                    } else {
                        TODO("VERSION.SDK_INT < O")
                    }
                }
            }
        }
    }

    private fun saveDiary() {
        viewModelScope.launch {
            val content = _uiState.value.content
            if (content.isNotBlank()) {
                saveDiaryUseCase(content)
            }
        }
    }

    private fun updateDiary() {
        viewModelScope.launch {
            val diaryId = _uiState.value.diaryId ?: return@launch
            val content = _uiState.value.content
            if (content.isNotBlank()) {
                updateDiaryUseCase(diaryId, content)
            }
        }
    }
}

data class WritingUiState(
    val content: String = "",
    val date: String = "",
    val diaryWritingType: DiaryWritingType = DiaryWritingType.WRITING,
    val diaryId: Int? = null,
    val isSaved: Boolean = false
)