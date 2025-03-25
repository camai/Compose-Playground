package com.jg.diary.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jg.composeplayground.core.model.navigation.DiaryWritingArgs
import com.jg.diary.screen.DiaryRoute
import com.jg.diary.screen.SecretKeypadRoute
import com.jg.diary.screen.WritingRoute

/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */
const val DIARY_ROUTE = "diary_route"
const val PASSWORD_ROUTE = "password_route"
const val WRITING_ROUTE = "writing_route"

fun NavHostController.navigateToDiary(navOptions: NavOptions? = null) =
    navigate(route = DIARY_ROUTE, navOptions = navOptions)

fun NavHostController.navigateToPassword(navOptions: NavOptions? = null) =
    navigate(route = PASSWORD_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.diaryScreen(
    onNavigateToWriting: (DiaryWritingArgs) -> Unit,
    onNavigationBack: () -> Unit
) {
    composable(route = DIARY_ROUTE) {
        DiaryRoute(
            onNavigateToWriting = onNavigateToWriting,
            onBackPress = onNavigationBack
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.writeScreen(
    onNavigationBack: () -> Unit
) {
    composable(
        route = WRITING_ROUTE,
        arguments = listOf(
            navArgument("args") {
                type = NavType.ParcelableType(DiaryWritingArgs::class.java)
                nullable = false
                defaultValue = DiaryWritingArgs()
            }
        )
    ) {
        WritingRoute(
            onNavigateBack = onNavigationBack
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