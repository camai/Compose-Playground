package com.jg.diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.composeplayground.core.domain.usecase.GetPassCodeUseCase
import com.jg.composeplayground.core.domain.usecase.SetPassCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *  추후 작업
 *  + 비밀번호 유효성 검사
 *  + 비밀번호 저장
 *  + 비밀번호 매칭 결과 확인
 */
@HiltViewModel
class SecretKeypadViewModel @Inject constructor(
    private val getPassCodeUseCase: GetPassCodeUseCase,
    private val setPassCodeUseCase: SetPassCodeUseCase
): ViewModel() {

    companion object {
        const val MAX_INPUT_LENGTH = 4
    }

    sealed class SecretKeypadIntent {
        data class AddNumber(val value: Char) : SecretKeypadIntent()
        object Clear : SecretKeypadIntent()
        object Done : SecretKeypadIntent()
        object Reset : SecretKeypadIntent()
    }

    private val _uiState = MutableStateFlow(SecretKeypadState())
    val uiState = _uiState.asStateFlow()

    private fun processIntent(intent: SecretKeypadIntent) {
        when (intent) {
            is SecretKeypadIntent.AddNumber -> addNumber(intent.value)
            is SecretKeypadIntent.Clear -> clear()
            is SecretKeypadIntent.Done -> done()
            is SecretKeypadIntent.Reset -> resetState()
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

    val resetState: () -> Unit = {
        processIntent(SecretKeypadIntent.Reset)
    }

    private fun addNumber(number: Char) {
        val currentLength = _uiState.value.password.length
        if (currentLength >= MAX_INPUT_LENGTH) return

        val update = _uiState.value.password + number
        // 상태 업데이트
        _uiState.update {
            it.copy(
                password = update,
                inputLength = update.length,
                passwordState = PasswordState.INPUT,
                errorMessage = null
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
                        PasswordState.INIT,
                    errorMessage = null
                )
            }
        }
    }

    private fun done() {
        val currentPassword = _uiState.value.password

        viewModelScope.launch {
            // 입력 길이 검증
            if (!validateInputLength(currentPassword)) return@launch
            
            // 패스코드 검증 및 처리
            getPassCodeUseCase().collect { savedPassCode ->
                when {
                    // 처음 설정하는 경우
                    savedPassCode.isEmpty() -> handleNewPasscodeSetup(currentPassword)
                    
                    // 기존 패스코드와 비교
                    savedPassCode == currentPassword -> handleSuccessfulLogin()
                    
                    // 패스코드 불일치
                    else -> handleInvalidPasscode()
                }
            }
        }
    }

    /**
     * 비밀번호 입력 길이 검증
     * @return 유효한 길이인지 여부
     */
    private fun validateInputLength(password: String): Boolean {
        if (password.length < MAX_INPUT_LENGTH) {
            _uiState.update { current ->
                current.copy(
                    passwordState = PasswordState.Warning,
                    errorMessage = "모두 입력해주세요"
                )
            }
            return false
        }
        return true
    }

    /**
     * 새 비밀번호 설정 처리
     */
    private suspend fun handleNewPasscodeSetup(password: String) {
        setPassCodeUseCase(password)
        _uiState.update { current ->
            current.copy(
                isDone = true,
                passwordState = PasswordState.INPUT,
                errorMessage = null
            )
        }
    }

    /**
     * 로그인 성공 처리
     */
    private fun handleSuccessfulLogin() {
        _uiState.update { current ->
            current.copy(
                isDone = true,
                passwordState = PasswordState.INPUT,
                errorMessage = null
            )
        }
    }

    /**
     * 패스코드 불일치 처리
     */
    private fun handleInvalidPasscode() {
        _uiState.update { current ->
            current.copy(
                passwordState = PasswordState.Warning,
                errorMessage = "입력된 번호가 틀렸습니다"
            )
        }
    }

    private fun resetState() {
        // 초기화 로직 구현
        _uiState.update {
            SecretKeypadState()
        }
    }
}

data class SecretKeypadState(
    val inputLength: Int = 0,
    val password: String = "",
    val passwordState: PasswordState = PasswordState.INIT,
    val isDone: Boolean = false,
    val errorMessage: String? = null
)

sealed class PasswordState {
    object INIT: PasswordState()
    object INPUT: PasswordState()
    object Warning: PasswordState()
}