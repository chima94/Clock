package com.example.clock.ui.main.alarm

sealed class AlarmUIActions {
    data class OpenDialog(val label: String = "") : AlarmUIActions()
    data class SetAlarm(val millis: Long, val am_pm: Int): AlarmUIActions()
}