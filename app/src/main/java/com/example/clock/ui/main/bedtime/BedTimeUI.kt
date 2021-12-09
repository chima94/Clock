package com.example.clock.ui.main.bedtime

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
fun BedTimeUI(){
    BedTimeUIScaffold()
}


@Composable
private fun BedTimeUIScaffold(){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.bedtime_topbar))
        },
        content = {

        }
    )
}