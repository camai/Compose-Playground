package com.jg.composeplayground.lotto.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.random.Random

// 무지개 색상 정의
private val rainbowColors = listOf(
    Color(0xFFFF0000), // 빨강
    Color(0xFFFF7F00), // 주황
    Color(0xFFFFFF00), // 노랑
    Color(0xFF00FF00), // 초록
    Color(0xFF0000FF), // 파랑
    Color(0xFF4B0082), // 남색
    Color(0xFF9400D3)  // 보라
)

/**
 * 로또 번호를 원형 배경에 표시하는 컴포넌트
 * 
 * @param number 표시할 로또 번호
 * @param size 원의 크기(dp)
 * @param colorSeed 색상 시드 (null이면 랜덤 색상 사용)
 */
@Composable
fun NumberCircle(
    number: Int,
    modifier: Modifier = Modifier,
    size: Int = 48,
    colorSeed: Int? = null
) {
    // 번호에 따라 색상 결정 또는 랜덤 색상 사용
    val colorIndex = colorSeed ?: Random.nextInt(rainbowColors.size)
    val backgroundColor = rainbowColors[colorIndex % rainbowColors.size]
    
    Box(
        modifier = modifier
            .size(size.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}