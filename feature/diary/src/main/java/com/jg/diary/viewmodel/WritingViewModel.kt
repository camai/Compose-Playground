package com.jg.diary.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.composeplayground.core.model.enums.DiaryWritingType
import com.jg.composeplayground.core.model.navigation.DiaryWritingArgs
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
        
        _uiState.update { current ->
            current.copy(
                date = formattedDateTime,
                isSaved = true
            )
        }
    }

    fun initDiary(data: DiaryWritingArgs) {
        when (data.diaryWritingType) {
            DiaryWritingType.WRITING -> {}
            DiaryWritingType.EDIT_POST -> {
                data.diaryId?.let { getPostContentToEdit(it) }
            }
        }
    }

    private fun getPostContentToEdit(diaryId: Int) {
        // TODO: 일기 내용 가져오기
    }

    fun saveDiary() {
        // TODO: 일기 저장
    }

    fun updateDiary() {
        // TODO: 일기 수정
    }
}

data class WritingUiState(
    val content: String = "",
    val date: String = "",
    val diaryWritingType: DiaryWritingType = DiaryWritingType.WRITING,
    val diaryId: Int? = null,
    val isSaved: Boolean = false
)