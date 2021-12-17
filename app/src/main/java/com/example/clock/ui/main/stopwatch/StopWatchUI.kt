package com.example.clock.ui.main.stopwatch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clock.R
import com.example.clock.ui.component.AppBar

@Composable
fun StopWatchUI(){
   StopWatchUIScaffold()
}

@Composable
private fun StopWatchUIScaffold(){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.stopwatch_topbar))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                StopWatchContent(
                    modifier = Modifier
                        .padding(bottom = 100.dp)
                        .size(230.dp)
                        .align(Alignment.Center)
                        .border(BorderStroke(6.dp, Color.Gray), shape = RoundedCornerShape(150.dp))

                )
                StartButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                )
            }
        }
    )
}



@Composable
private fun StopWatchContent(modifier: Modifier){
    Box(modifier = modifier){
        Row(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = "0",
                fontSize = 50.sp,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .alignByBaseline()
            )
            Text(
                text = "00",
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Composable
private fun StartButton(modifier: Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        TextButton(
            onClick = { /*TODO*/ },
        ) {
            Text(text = "Reset")
        }

        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = "start"
            )
        }
        TextButton(
            onClick = { /*TODO*/ },
        ) {
            Text(text = "Share")
        }
    }
}

