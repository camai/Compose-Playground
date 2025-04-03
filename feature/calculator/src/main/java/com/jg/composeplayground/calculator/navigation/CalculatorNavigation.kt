package com.jg.composeplayground.calculator.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jg.composeplayground.calculator.screen.CalculatorRoute


/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */

const val CALCULATOR_ROUTE = "calculator_route"

fun NavGraphBuilder.calculatorScreen(
    onBackPress: () -> Unit
) {
    composable(route = CALCULATOR_ROUTE) {
        CalculatorRoute(
            onBackPress = onBackPress
        )
    }
}