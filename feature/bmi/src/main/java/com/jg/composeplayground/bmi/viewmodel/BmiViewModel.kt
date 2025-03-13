package com.jg.composeplayground.bmi.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round
import java.text.DecimalFormat

data class BmiState(
    val height: String = "",
    val weight: String = "",
    val bmiResult: Double = 0.0,
    val bmiCategory: String = "",
    val isCalculated: Boolean = false
)

@HiltViewModel
class BmiViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(BmiState())
    val uiState: StateFlow<BmiState> = _uiState.asStateFlow()
    
    // 소수점 두자리까지만 표시하기 위한 포맷터
    private val decimalFormat = DecimalFormat("#.##")
    
    fun updateHeight(height: String) {
        _uiState.update { it.copy(height = height) }
    }
    
    fun updateWeight(weight: String) {
        _uiState.update { it.copy(weight = weight) }
    }
    
    fun calculateBmi() {
        val height = _uiState.value.height.toFloatOrNull() ?: return
        val weight = _uiState.value.weight.toFloatOrNull() ?: return
        
        if (height <= 0 || weight <= 0) return
        
        // BMI = 체중(kg) / (키(m) * 키(m))
        // 키는 cm로 입력받으므로 m로 변환
        val heightInMeter = height / 100
        val bmi = weight / heightInMeter.pow(2)
        
        // 소수점 둘째자리까지 표시하도록 수정
        val roundedBmi = decimalFormat.format(bmi).toDouble()
        
        val category = when {
            roundedBmi < 18.5 -> "저체중"
            roundedBmi < 23.0 -> "정상"
            roundedBmi < 25.0 -> "과체중"
            roundedBmi < 30.0 -> "비만"
            else -> "고도비만"
        }
        
        _uiState.update { 
            it.copy(
                bmiResult = roundedBmi,
                bmiCategory = category,
                isCalculated = true
            )
        }
    }
    
    fun resetBmi() {
        _uiState.update { 
            it.copy(
                height = "",
                weight = "",
                bmiResult = 0.0,
                bmiCategory = "",
                isCalculated = false
            )
        }
    }
}