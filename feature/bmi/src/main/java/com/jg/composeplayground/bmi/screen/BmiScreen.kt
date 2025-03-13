package com.jg.composeplayground.bmi.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.composeplayground.bmi.viewmodel.BmiState
import com.jg.composeplayground.bmi.viewmodel.BmiViewModel

@Composable
internal fun BmiRoute(
    viewModel: BmiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BmiScreen(
        uiState = uiState,
        updateHeight = viewModel::updateHeight,
        updateWeight = viewModel::updateWeight,
        calculateBmi = viewModel::calculateBmi,
        resetBmi = viewModel::resetBmi
    )
}



@Composable
private fun BmiScreen(
    modifier: Modifier = Modifier,
    uiState: BmiState,
    updateHeight: (height: String) -> Unit,
    updateWeight: (weight: String) -> Unit,
    calculateBmi: () -> Unit,
    resetBmi: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BMI 계산기",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // 키 입력 필드
                OutlinedTextField(
                    value = uiState.height,
                    onValueChange = { updateHeight(it) },
                    label = { Text("키 (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 체중 입력 필드
                OutlinedTextField(
                    value = uiState.weight,
                    onValueChange = { updateWeight(it) },
                    label = { Text("체중 (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 계산 버튼
                Button(
                    onClick = { calculateBmi() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("BMI 계산하기")
                }

                // 결과 표시
                if (uiState.isCalculated) {
                    BmiResultCard(
                        bmiResult = uiState.bmiResult,
                        bmiCategory = uiState.bmiCategory,
                        resetBmi = resetBmi
                    )
                }
            }
        }
    }
}

@Composable
private fun BmiResultCard(
    bmiResult: Double,
    bmiCategory: String,
    resetBmi: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "당신의 BMI",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = bmiResult.toString(),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = bmiCategory,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = getBmiDescription(bmiCategory),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { resetBmi() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("다시 계산하기")
    }
}

@Composable
private fun getBmiDescription(category: String): String {
    return when (category) {
        "저체중" -> "건강을 위해 체중 증가가 필요할 수 있습니다. 영양가 있는 식단과 근력 운동을 고려해보세요."
        "정상" -> "건강한 체중 범위에 있습니다. 균형 잡힌 식단과 꾸준한 운동으로 유지하세요."
        "과체중" -> "약간의 체중 감량이 건강에 도움이 될 수 있습니다. 식이 조절과 규칙적인 운동을 시작해보세요."
        "비만" -> "건강 위험을 줄이기 위해 체중 관리가 필요합니다. 의료 전문가와 상담하는 것이 좋습니다."
        "고도비만" -> "심각한 건강 위험이 있을 수 있습니다. 반드시 의료 전문가와 상담하여 체중 관리 계획을 세우세요."
        else -> ""
    }
}