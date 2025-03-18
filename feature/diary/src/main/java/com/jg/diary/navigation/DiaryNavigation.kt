package com.jg.diary.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.diary.screen.DiaryRoute

/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */
const val DIARY_ROUTE = "diary_route"

fun NavHostController.navigateToDiary(navOptions: NavOptions? = null) =
    navigate(route = DIARY_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.diaryScreen(onNavigationBack: () -> Unit) {
    composable(route = DIARY_ROUTE) {
        DiaryRoute(
            onBackPress = onNavigationBack
        )
    }
}