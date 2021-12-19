package com.example.clock.ui.main.alarm

import com.example.clock.business.datasources.alarm.AlarmEntity

sealed class AlarmUIActions {
    data class OpenDialog(val label: String = "", val alarmEntity: AlarmEntity? = null) : AlarmUIActions()
    data class SetAlarm(val millis: Long, val am_pm: Int): AlarmUIActions()
    data class UpdateAlarm(val alarmEntity: AlarmEntity): AlarmUIActions()
    data class DeleteAlarm(val millis: Long): AlarmUIActions()
}