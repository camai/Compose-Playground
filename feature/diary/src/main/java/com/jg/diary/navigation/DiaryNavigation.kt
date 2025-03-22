package com.jg.diary.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.diary.screen.DiaryRoute
import com.jg.diary.screen.SecretKeypadRoute
import com.jg.diary.viewmodel.SecretKeypadViewModel

/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */
const val DIARY_ROUTE = "diary_route"
const val PASSWORD_ROUTE = "password_route"

fun NavHostController.navigateToDiary(navOptions: NavOptions? = null) =
    navigate(route = DIARY_ROUTE, navOptions = navOptions)

fun NavHostController.navigateToPassword(navOptions: NavOptions? = null) =
    navigate(route = PASSWORD_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.diaryScreen(onNavigationBack: () -> Unit) {
    composable(route = DIARY_ROUTE) {
        DiaryRoute(
            onBackPress = onNavigationBack
        )
    }
}

fun NavGraphBuilder.passwordScreen(
    onNavigationBack: () -> Unit,
    navToDiary: () -> Unit
) {
    composable(route = PASSWORD_ROUTE) {
        SecretKeypadRoute(
            onBackPress = onNavigationBack,
            onSuccess = {
                navToDiary()
            }
        )
    }
}