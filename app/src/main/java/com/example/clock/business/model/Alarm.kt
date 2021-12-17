package com.example.clock.business.model

import com.example.clock.business.datasources.alarm.AlarmEntity

data class SetAlarm(
    val id: Int = -1,
    val millis: Long,
    val timeState: Int = 0,
    val isSetAlarm: Boolean,
    val repeat: List<WeekDays>
)

data class WeekDays(
    val day: String,
    val repeatState: Boolean
)


fun SetAlarm.toAlarmEntity(): AlarmEntity{
    return AlarmEntity(
        millis = millis,
        timeState = timeState,
        isSetAlarm = isSetAlarm,
        repeat = repeat
    )
}