package com.example.clock.di

import android.content.Context
import androidx.room.Room
import com.example.clock.business.datasources.AppDatabase
import com.example.clock.business.datasources.AppDatabase.Companion.DATABASE_NAME
import com.example.clock.business.datasources.alarm.AlarmDao
import com.example.clock.service.AlarmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase{
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlarmDao(db: AppDatabase): AlarmDao{
        return db.getAlarmDao()
    }

    @Singleton
    @Provides
    fun provideAlarmService(@ApplicationContext context: Context): AlarmService{
        return AlarmService(context = context)
    }
}