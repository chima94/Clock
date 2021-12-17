package com.example.clock.business.datasources

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.clock.business.datasources.alarm.AlarmDao
import com.example.clock.business.datasources.alarm.AlarmEntity
import com.example.clock.business.datasources.alarm.Converter


@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}