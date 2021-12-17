package com.example.clock.ui.main.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clock.business.datasources.alarm.AlarmDao
import com.example.clock.business.model.SetAlarm
import com.example.clock.business.model.WeekDays
import com.example.clock.business.model.toAlarmEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDao: AlarmDao
): ViewModel(){


    val alarm = alarmDao.getAllAlarm()


    fun insertAlarm(alarm: SetAlarm){
        viewModelScope.launch {
            alarmDao.insertAlarm(alarmEntity = alarm.toAlarmEntity())
        }
    }



    fun weekdays(): List<WeekDays> =
        listOf(
            WeekDays(
                day = "S",
                repeatState = true
            ),
            WeekDays(
                day = "M",
                repeatState = true
            ),
            WeekDays(
                day = "T",
                repeatState = true
            ),
            WeekDays(
                day = "W",
                repeatState = true
            ),
            WeekDays(
                day = "T",
                repeatState = true
            ),
            WeekDays(
                day = "F",
                repeatState = true
            ),
            WeekDays(
                day = "S",
                repeatState = true
            )
        )
}