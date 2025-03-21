package com.jg.diary.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


/**
 *  추후 작업
 *  + 비밀번호 유효성 검사
 *  + 비밀번호 저장
 *  + 비밀번호 매칭 결과 확인
 */
@HiltViewModel
class SecretKeypadViewModel @Inject constructor(

): ViewModel() {

    companion object {
        const val MAX_INPUT_LENGTH = 4
    }

    sealed class SecretKeypadIntent {
        data class AddNumber(val value: Char) : SecretKeypadIntent()
        object Clear : SecretKeypadIntent()
        object Done : SecretKeypadIntent()
    }

    private val _uiState = MutableStateFlow(SecretKeypadState())
    val uiState = _uiState.asStateFlow()

    private fun processIntent(intent: SecretKeypadIntent) {
        when (intent) {
            is SecretKeypadIntent.AddNumber -> addNumber(intent.value)
            is SecretKeypadIntent.Clear -> clear()
            is SecretKeypadIntent.Done -> done()
        }
    }

    val addNumber: (Char) -> Unit = { value ->
        processIntent(SecretKeypadIntent.AddNumber(value))
    }

    val clear: () -> Unit = {
        processIntent(SecretKeypadIntent.Clear)
    }

    val done: () -> Unit = {
        processIntent(SecretKeypadIntent.Done)
    }

    private fun addNumber(number: Char) {
        val currentLength = _uiState.value.password.length
        if (currentLength >= MAX_INPUT_LENGTH) return

        val update = _uiState.value.password + number
        _uiState.update {
            it.copy(
                password = update,
                inputLength = update.length,
                passwordState = PasswordState.INPUT
            )
        }
    }

    private fun clear() {
        val currentPassword = _uiState.value.password
        if (currentPassword.isNotEmpty()) {
            // 마지막 글자만 제거
            val updatedPassword = currentPassword.dropLast(1)

            _uiState.update {
                it.copy(
                    password = updatedPassword,
                    inputLength = updatedPassword.length,
                    // 비밀번호가 있으면 INPUT 상태 유지, 없으면 INIT으로 변경
                    passwordState = if (updatedPassword.isNotEmpty())
                        PasswordState.INPUT
                    else
                        PasswordState.INIT
                )
            }
        }
    }

    private fun done() {
        // 저장 로직 구현
        _uiState.update {
            it.copy(
                isDone = true,
                passwordState = PasswordState.INPUT
            )
        }
    }

    // 보완 필요
    override fun onCleared() {
        _uiState.update {
            it.copy(
                isDone = false,
                passwordState = PasswordState.INIT,
                password = "",
                inputLength = 0
            )
        }
        super.onCleared()
    }
}

data class SecretKeypadState(
    val inputLength: Int = 0,
    val password: String = "",
    val passwordState: PasswordState = PasswordState.INIT,
    val isDone: Boolean = false
)

sealed class PasswordState {
    object INIT: PasswordState()
    object INPUT: PasswordState()
    object Warning: PasswordState()
}