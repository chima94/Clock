package com.example.clock.ui.main.timer

sealed class TimerActions{
    data class NumberClicked(val number: String): TimerActions()
}
