package com.example.clock.ui.main.clock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ClockUI(){

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "clock Screen"
        )
    }
}