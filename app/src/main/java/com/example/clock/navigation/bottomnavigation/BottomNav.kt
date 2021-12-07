package com.example.clock.navigation.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.res.stringResource
import com.example.clock.R

object AlarmRoute: BottomNavigationEntry(ALARM, R.string.alarm)
object BedTimeRoute: BottomNavigationEntry(BEDTIME, R.string.bedtime)
object ClockRoute: BottomNavigationEntry(CLOCK, R.string.clock)
object TimerRoute: BottomNavigationEntry(TIMER, R.string.timer)
object StopWatchRoute: BottomNavigationEntry(STOPWATCH, R.string.stopwatch)


val bottomNavigationEntries = listOf(
    BottomNavigationUIEntry(
        AlarmRoute,
        Icons.Filled.Alarm,
        Icons.Outlined.AccessAlarm
    ),

    BottomNavigationUIEntry(
        ClockRoute,
        Icons.Filled.AccessTime,
        Icons.Outlined.AccessTime
    ),
    BottomNavigationUIEntry(
        TimerRoute,
        Icons.Filled.HourglassBottom,
        Icons.Outlined.HourglassBottom
    ),
    BottomNavigationUIEntry(
        StopWatchRoute,
        Icons.Filled.Timer,
        Icons.Outlined.Timer
    ),
    BottomNavigationUIEntry(
        BedTimeRoute,
        Icons.Filled.KingBed,
        Icons.Outlined.KingBed
    ),

)