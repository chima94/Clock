package com.example.clock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.clock.navigation.bottomnavigation.*
import com.example.clock.ui.main.alarm.AlarmUI
import com.example.clock.ui.main.bedtime.BedTimeUI
import com.example.clock.ui.main.clock.ClockUI
import com.example.clock.ui.main.stopwatch.StopWatchUI
import com.example.clock.ui.main.timer.TimerUI

private val destinationsBottomNav: Map<BottomNavigationEntry, @Composable ()-> Unit> = mapOf(

    AlarmRoute to { AlarmUI()},
    ClockRoute to { ClockUI()},
    TimerRoute to { TimerUI()},
    BedTimeRoute to { BedTimeUI()},
    StopWatchRoute to { StopWatchUI()}
)

fun NavGraphBuilder.addBottomNavigationDestions(){
    destinationsBottomNav.forEach{entry ->
        val destination = entry.key
        composable(destination.route){
            entry.value()
        }
    }
}