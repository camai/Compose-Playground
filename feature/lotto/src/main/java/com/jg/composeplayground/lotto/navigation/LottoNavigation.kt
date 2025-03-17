package com.jg.composeplayground.lotto.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jg.composeplayground.lotto.screen.LottoRoute


/**
 *  추후 리펙토링 필요
 *  + Navigation 플로우
 */

const val LOTTO_ROUTE = "lotto_route"

fun NavHostController.navigateToLotto(navOptions: NavOptions? = null) =
    navigate(route = LOTTO_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.lottoScreen(onNavigationBack: () -> Unit) {
    composable(route = LOTTO_ROUTE) {
        LottoRoute(
            onBackPress = { onNavigationBack() }
        )
    }
}