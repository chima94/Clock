package com.example.clock.ui.main.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TimerUI(){

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Timer Screen"
        )
    }
}