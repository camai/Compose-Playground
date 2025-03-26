package com.jg.composeplayground.core.model.navigation

import com.jg.composeplayground.core.model.enums.DiaryWritingType
import com.jg.composeplayground.navigation.BaseNavArgs
import com.jg.composeplayground.navigation.createIntNavArgument
import com.jg.composeplayground.navigation.createStringNavArgument
import kotlinx.serialization.Serializable

@Serializable
data class DiaryWritingArgs(
    val diaryId: Int? = null,
    val diaryWritingType: DiaryWritingType = DiaryWritingType.WRITING,
    val content: String = "",
    val date: String = ""
): java.io.Serializable, BaseNavArgs(
    fieldTypes = mapOf(
        "diaryId" to ::createIntNavArgument,
        "content" to ::createStringNavArgument,
        "date" to ::createStringNavArgument,
        "type" to ::createIntNavArgument
    ),
    fieldDefaults = mapOf(
        "diaryId" to 0,
        "content" to "",
        "date" to "",
        "type" to 0
    )
) {
    override fun toNavArgs(): Map<String, Any?> {
        return mapOf(
            "diaryId" to (diaryId ?: 0),
            "content" to content,
            "date" to date,
            "type" to diaryWritingType.ordinal
        )
    }
}
