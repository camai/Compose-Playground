package com.jg.composeplayground.domain.model

import java.time.LocalDateTime

data class Diary(
    val id: Int = 0,
    val content: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) 