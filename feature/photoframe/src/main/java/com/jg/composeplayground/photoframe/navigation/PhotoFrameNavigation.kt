package com.jg.composeplayground.photoframe.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jg.composeplayground.photoframe.screen.PhotoFrameRoute

/**
*  추후 리펙토링 필요
*  + Navigation 플로우
*/

const val PHOTO_FRAME_ROUTE = "photo_frame_route"

fun NavGraphBuilder.photoFrameScreen(
    onBackPress: () -> Unit
) {
    composable(route = PHOTO_FRAME_ROUTE) {
        PhotoFrameRoute(
            onBackPress = onBackPress
        )
    }
}