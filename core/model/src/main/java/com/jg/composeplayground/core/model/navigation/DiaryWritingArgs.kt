package com.jg.composeplayground.core.model.navigation

import com.jg.composeplayground.core.model.enums.DiaryWritingType
import kotlinx.serialization.Serializable

@Serializable
data class DiaryWritingArgs(
    val diaryId: Int,
    val diaryWritingType: DiaryWritingType
): java.io.Serializable
