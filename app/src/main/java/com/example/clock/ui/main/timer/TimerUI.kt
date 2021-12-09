package com.example.clock.ui.main.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.clock.R
import com.example.clock.ui.component.AppBar


@Composable
fun TimerUI(){

    TimerUIScaffold()
}

@Composable
private fun TimerUIScaffold(){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.timer_topbar))
        },
        content = {

        }
    )
}