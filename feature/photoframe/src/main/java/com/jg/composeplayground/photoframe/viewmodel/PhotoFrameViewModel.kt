package com.jg.composeplayground.photoframe.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoFrameViewModel @Inject constructor(

): ViewModel() {
    // UI 상태를 위한 StateFlow
    private val _uiState = MutableStateFlow(PhotoFrameUiState())
    val uiState: StateFlow<PhotoFrameUiState> = _uiState.asStateFlow()

    // 사진 URI를 처리하는 함수
    fun handleImageSelected(uri: Uri) {
        viewModelScope.launch {
            try {
                // 사진 URI 상태 업데이트
                _uiState.update { currentState ->
                    currentState.copy(
                        selectedPhotoUri = uri,
                        hasPhoto = true
                    )
                }
                
            } catch (e: Exception) {
                // 오류 처리
                _uiState.update { currentState ->
                    currentState.copy(
                        errorMessage = e.message ?: "사진 처리 중 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    // 오류 메시지 초기화
    fun clearErrorMessage() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }
}

// UI 상태를 나타내는 데이터 클래스
data class PhotoFrameUiState(
    val selectedPhotoUri: Uri? = null,
    val hasPhoto: Boolean = false,
    val errorMessage: String? = null
)