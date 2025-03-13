package com.jg.composeplayground.bmi.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.composeplayground.bmi.screen.BmiRoute


private val BMIROUTE = "bmi_route"

fun NavController.navigateToBmi(navOptions: NavOptions) =
    navigate(route = BMIROUTE, navOptions)

fun NavGraphBuilder.bmiScreen() {
    composable(route = BMIROUTE) {
        BmiRoute()
    }
}