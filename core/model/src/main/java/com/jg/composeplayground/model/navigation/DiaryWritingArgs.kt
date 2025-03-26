package com.jg.composeplayground.model.navigation

import com.jg.composeplayground.model.enums.DiaryWritingType
import kotlinx.serialization.Serializable

@Serializable
data class DiaryWritingArgs(
    val diaryId: Int? = null,
    val diaryWritingType: DiaryWritingType = DiaryWritingType.WRITING,
    val content: String = "",
    val date: String = ""
): java.io.Serializable