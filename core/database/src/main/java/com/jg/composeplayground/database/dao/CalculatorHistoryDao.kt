package com.jg.composeplayground.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jg.composeplayground.database.entity.CalculatorHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculatorHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: CalculatorHistoryEntity)

    @Query("DELETE FROM calculator_histories")
    suspend fun deleteAllHistory()

    @Query("SELECT * FROM calculator_histories ORDER BY id DESC")
    fun getAllHistories(): Flow<List<CalculatorHistoryEntity>>
}