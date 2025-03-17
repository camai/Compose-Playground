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
        val numbers: List<Int> = emptyList(),
        val isComplete: Boolean = false
    )

    sealed class LottoIntent {
        data class AddNumber(val number: Int) : LottoIntent()
        object GenerateRandom : LottoIntent()
        object Clear : LottoIntent()
    }

    private val _uiState = MutableStateFlow(LottoState())
    val uiState: StateFlow<LottoState> = _uiState.asStateFlow()


    fun processIntent(intent: LottoIntent) {
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

        if (currentNumbers.size >= 6 || number in currentNumbers) return

        val newNumbers = (currentNumbers + number).sorted()
        _uiState.value = _uiState.value.copy(
            numbers = newNumbers,
            isComplete = newNumbers.size == 6
        )
    }

    private fun generateRandomNumbers() {
        val currentNumbers = _uiState.value.numbers

        if (currentNumbers.isEmpty()) {
            generateAllRandomNumbers()
            return
        }

        val remainingCount = 6 - currentNumbers.size
        if (remainingCount <= 0) return

        val additionalNumbers = generateSequence {
            Random.nextInt(from = 1, until = 46)
        }.distinct()
            .filter { it !in currentNumbers }
            .take(remainingCount)
            .toList()

        val newNumbers = (currentNumbers + additionalNumbers).sorted()

        _uiState.value = _uiState.value.copy(
            numbers = newNumbers,
            isComplete = true
        )
    }

    private fun generateAllRandomNumbers() {
        val numbers = generateSequence {
            Random.nextInt(from = 1, until = 46)
        }.distinct().take(6).sorted().toList()

        _uiState.value = _uiState.value.copy(
            numbers = numbers,
            isComplete = true
        )
    }

    private fun clearNumbers() {
        _uiState.value = LottoState()
    }
}