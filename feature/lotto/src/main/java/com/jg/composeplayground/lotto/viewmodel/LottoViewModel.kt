package com.jg.composeplayground.lotto.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LottoViewModel @Inject constructor() : ViewModel() {

    data class LottoState(
        val numbers: Set<Int> = emptySet(),
        val isComplete: Boolean = false,
        val manualNumbers: Set<Int> = emptySet() // 수동으로 입력한 번호 추적
    ) {
        // 정렬된 번호 목록을 제공하는 계산 속성
        val sortedNumbers: List<Int>
            get() = numbers.toList().sorted()
    }

    sealed class LottoIntent {
        data class AddNumber(val number: Int) : LottoIntent()
        object GenerateRandom : LottoIntent()
        object Clear : LottoIntent()
    }

    private val _uiState = MutableStateFlow(LottoState())
    val uiState: StateFlow<LottoState> = _uiState.asStateFlow()


    private fun processIntent(intent: LottoIntent) {
        when (intent) {
            is LottoIntent.AddNumber -> addNumber(intent.number)
            is LottoIntent.GenerateRandom -> generateRandomNumbers()
            is LottoIntent.Clear -> clearNumbers()
        }
    }

    // 기존 메소드는 프로퍼티로 노출
    val updateLottoNumber: (Int) -> Unit = { number ->
        processIntent(LottoIntent.AddNumber(number))
    }

    val randomLottoNumbers: () -> Unit = {
        processIntent(LottoIntent.GenerateRandom)
    }

    val clearLottoNumbers: () -> Unit = {
        processIntent(LottoIntent.Clear)
    }

    // 로직 구현 - private 함수로 분리
    private fun addNumber(number: Int) {
        val currentNumbers = _uiState.value.numbers
        val currentManualNumbers = _uiState.value.manualNumbers

        if (currentNumbers.size >= 6 || number in currentNumbers) return

        val newNumbers = currentNumbers + number
        // 수동 입력 번호도 추적
        val newManualNumbers = currentManualNumbers + number
        
        _uiState.value = _uiState.value.copy(
            numbers = newNumbers,
            manualNumbers = newManualNumbers,
            isComplete = newNumbers.size == 6
        )
    }

    private fun generateRandomNumbers() {
        val currentManualNumbers = _uiState.value.manualNumbers

        // 이미 6개 모두 수동으로 입력했으면 변경하지 않음
        if (currentManualNumbers.size >= 6) return

        // 효율적인 방식으로 변경: 가능한 번호 풀을 만들고 셔플
        val availableNumbers = (1..45).filter { it !in currentManualNumbers }
        val additionalNumbers = availableNumbers.shuffled()
            .take(6 - currentManualNumbers.size)
            .toSet()

        val newNumbers = currentManualNumbers + additionalNumbers

        _uiState.value = _uiState.value.copy(
            numbers = newNumbers,
            isComplete = true
        )
    }

    private fun clearNumbers() {
        _uiState.value = LottoState()
    }
}