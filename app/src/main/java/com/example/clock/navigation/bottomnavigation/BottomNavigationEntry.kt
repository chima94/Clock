package com.example.clock.navigation.bottomnavigation

import androidx.annotation.StringRes

sealed class BottomNavigationEntry(val route: String, @StringRes val resourcesID: Int){
    companion object{

        const val ALARM = "alarm"
        const val BEDTIME = "bedtime"
        const val CLOCK = "clock"
        const val STOPWATCH = "stopwatch"
        const val TIMER = "timer"
    }
}