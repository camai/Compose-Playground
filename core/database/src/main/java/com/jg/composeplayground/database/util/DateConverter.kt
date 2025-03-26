package com.jg.composeplayground.database.util

import android.os.Build
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * LocalDateTime을 문자열로 변환하고 그 반대로도 변환하는 Room 타입 컨버터
 */
class DateConverter {
    private val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.parse(it, formatter)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date?.format(formatter)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
} 