package com.example.clock.ui.main.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clock.business.datasources.alarm.AlarmDao
import com.example.clock.business.datasources.alarm.AlarmEntity
import com.example.clock.business.model.SetAlarm
import com.example.clock.business.model.WeekDays
import com.example.clock.business.model.toAlarmEntity
import com.example.clock.service.AlarmService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmService: AlarmService
): ViewModel(){


    val alarm = alarmDao.getAllAlarm()


    fun insertAlarm(alarmEntity: AlarmEntity){
        viewModelScope.launch {
            alarmDao.insertAlarm(alarmEntity = alarmEntity)
        }
    }


    fun deleteAlarm(millis: Long){
        viewModelScope.launch {
            alarmDao.deleteAlarm(millis)
        }
    }


    fun setAlarm(millis: Long){
        viewModelScope.launch(Dispatchers.Main){
            alarmService.setRepetitiveAlarm(millis)
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