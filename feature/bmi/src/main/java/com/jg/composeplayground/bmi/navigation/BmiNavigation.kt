package com.jg.composeplayground.bmi.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.composeplayground.bmi.screen.BmiRoute


const val BMI_ROUTE = "bmi_route"

fun NavHostController.navigateToBmi(navOptions: NavOptions? = null) =
    navigate(route = BMI_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.bmiScreen() {
    composable(route = BMI_ROUTE) {
        BmiRoute()
    }
}