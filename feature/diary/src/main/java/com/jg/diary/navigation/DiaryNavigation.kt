package com.jg.diary.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.composeplayground.common.common.navigation.*
import com.jg.composeplayground.core.model.enums.DiaryWritingType
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

// JSON 파라미터 이름
private const val PARAM_JSON = "json"


fun NavHostController.navigateToDiary(navOptions: NavOptions? = null) =
    navigate(route = DIARY_ROUTE, navOptions = navOptions)

fun NavHostController.navigateToPassword(navOptions: NavOptions? = null) =
    navigate(route = PASSWORD_ROUTE, navOptions = navOptions)

fun NavHostController.navigateToWriting(args: DiaryWritingArgs, navOptions: NavOptions? = null) {
    navigate(route = args.toJsonRoute(WRITING_ROUTE, PARAM_JSON), navOptions = navOptions)
}

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
    val routePlaceholder = createJsonRoutePlaceholder(WRITING_ROUTE, PARAM_JSON)
    
    composable(
        route = routePlaceholder,
        arguments = listOf(createJsonNavArgument(PARAM_JSON))
    ) { backStackEntry ->
        val args = backStackEntry.getJsonArg<DiaryWritingArgs>(PARAM_JSON) ?: DiaryWritingArgs()
        WritingRoute(
            argument = args,
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