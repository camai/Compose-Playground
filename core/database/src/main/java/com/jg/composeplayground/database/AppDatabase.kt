package com.jg.composeplayground.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jg.composeplayground.database.dao.CalculatorHistoryDao
import com.jg.composeplayground.database.dao.DiaryDao
import com.jg.composeplayground.database.entity.CalculatorHistoryEntity
import com.jg.composeplayground.database.entity.DiaryEntity
import com.jg.composeplayground.database.util.DateConverter

@Database(
    entities = [DiaryEntity::class, CalculatorHistoryEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao
    abstract fun calculatorHistoryDao(): CalculatorHistoryDao

    companion object {
        private const val DATABASE_NAME = "compose_playground.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .addMigrations(MIGRATION_1_2)
            .build()
        }
    }
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `calculator_histories` " +
            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "`expression` TEXT NOT NULL, " +
            "`result` TEXT NOT NULL)"
        )
    }
} 