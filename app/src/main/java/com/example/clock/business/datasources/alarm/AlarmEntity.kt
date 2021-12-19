package com.example.clock.business.datasources.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.clock.business.model.WeekDays
import java.util.*


@Entity(tableName = "alarm_table")
data class AlarmEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "millis")
    val millis: Long,

    @ColumnInfo(name = "timeState")
    val timeState: Int,

    @ColumnInfo(name = "isSetAlarm")
    var isSetAlarm: Boolean,

    @ColumnInfo(name = "label")
    val label : String,

    @ColumnInfo(name = "repeat")
    val repeat: List<WeekDays>,

    @ColumnInfo(name = "expanded")
    var expanded: Boolean = true
)


