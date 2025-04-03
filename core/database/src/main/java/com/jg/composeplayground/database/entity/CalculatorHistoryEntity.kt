package com.jg.composeplayground.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator_histories")
data class CalculatorHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val expression: String,
    val result: String,
)
