package com.jg.composeplayground.database.di

import android.content.Context
import com.jg.composeplayground.data.database.DiaryLocalDataSource
import com.jg.composeplayground.database.AppDatabase
import com.jg.composeplayground.database.dao.DiaryDao
import com.jg.composeplayground.database.source.DiaryLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDiaryDao(appDatabase: AppDatabase): DiaryDao {
        return appDatabase.diaryDao()
    }

    @Provides
    @Singleton
    fun provideDiaryLocalDataSource(diaryDao: DiaryDao): DiaryLocalDataSource {
        return DiaryLocalDataSourceImpl(diaryDao)
    }
} 