package com.example.clock.business.model

import com.example.clock.business.datasources.alarm.AlarmEntity

data class SetAlarm(
    var millis: Long = -1,
    var timeState: Int = 0,
    var isSetAlarm: Boolean = true,
    var label: String = "",
    var repeat: List<WeekDays> = emptyList()
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
        label = label,
        repeat = repeat
    )
}


fun AlarmEntity.toSetAlarm(): SetAlarm{
    return SetAlarm(
        millis = millis,
        timeState = timeState,
        isSetAlarm = isSetAlarm,
        label = label,
        repeat = repeat
    )
}