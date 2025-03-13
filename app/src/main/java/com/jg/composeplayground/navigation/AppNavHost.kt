package com.jg.composeplayground.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jg.composeplayground.home.HomeScreen
import com.jg.composeplayground.bmi.screen.BmiRoute

object AppDestinations {
    const val HOME_ROUTE = "home"
    const val BMI_ROUTE = "bmi"
    // 추후 다른 경로들 추가
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
                    navController.navigate(AppDestinations.BMI_ROUTE)
                }
            )
        }
        
        composable(AppDestinations.BMI_ROUTE) {
            BmiRoute()
        }
        
        // 추후 다른 화면들 추가
    }
} 