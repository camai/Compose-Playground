package com.jg.composeplayground.bmi.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.composeplayground.bmi.screen.BmiRoute

/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */
const val BMI_ROUTE = "bmi_route"

fun NavHostController.navigateToBmi(navOptions: NavOptions? = null) =
    navigate(route = BMI_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.bmiScreen(onNavigationBack: () -> Unit) {
    composable(route = BMI_ROUTE) {
        BmiRoute(
            onBackPress = onNavigationBack
        )
    }
}