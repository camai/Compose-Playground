package com.jg.composeplayground.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jg.composeplayground.bmi.navigation.BMI_ROUTE
import com.jg.composeplayground.bmi.navigation.bmiScreen
import com.jg.composeplayground.home.HomeScreen

object AppDestinations {
    const val HOME_ROUTE = "home"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AppDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME_ROUTE) {
            HomeScreen(
                onBmiButtonClick = {
                    // BmiNavigation 확장 함수를 사용하여 BMI 화면으로 이동
                    // launchSingleTop: 백스택 최상단에 이미 있으면 재사용
                    // 백스택 관리 (Home -> BMI)
                    navController.navigate(BMI_ROUTE) {
                        // 중복 방지
                        launchSingleTop = true
                        // 백스택 보존
                        restoreState = true
                    }
                }
            )
        }

        // BMI 화면 라우트 추가 - 모듈화된 네비게이션 함수 사용
        bmiScreen()
    }
} 