package com.example.clock.ui.main.timer

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Box(modifier = Modifier.fillMaxSize()){
                TimerContent(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth()
                )
            }
        }
    )
}


@Composable
private fun TimerContent(modifier : Modifier){
    Column(
        modifier = modifier
    ) {
        TimerRow()
        Spacer(modifier = Modifier.height(40.dp))
        Divider(modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .height(1.dp)
        )
        KeyBoardRow(
            number1 = "1",
            number2 = "2",
            number3 = "3",
        )
        KeyBoardRow(
            number1 = "4",
            number2 = "5",
            number3 = "6",
        )
        KeyBoardRow(
            number1 = "7",
            number2 = "8",
            number3 = "9"
        )
        Row(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "0",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}


@Composable
private fun TimerRow(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        TimeRow(text1 = "00", text2 = "h")
        TimeRow(text1 = "00", text2 = "m")
        TimeRow(text1 = "00", text2 = "s")
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(top = 23.dp, start = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Backspace,
                contentDescription = "backspace",
            )
        }


    }
}





@Composable
private fun TimeRow(text1: String, text2: String){
    Row(modifier = Modifier.padding(8.dp)){
        Text(
            text = text1,
            fontSize = 50.sp,
            modifier = Modifier
                .padding(4.dp)
                .alignByBaseline()
        )
        Text(
            text = text2,
            fontSize = 22.sp,
            modifier = Modifier.alignByBaseline()
        )
    }
}


@Composable
private fun KeyBoardRow(
    number1: String,
    number2: String,
    number3: String,
){
    Row(
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = number1,
                fontSize = 40.sp,
                fontWeight = FontWeight.W400,
            )
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = number2,
                fontSize = 40.sp,
                fontWeight = FontWeight.W400,
            )
        }
       TextButton(onClick = { /*TODO*/ }) {
           Text(
               text = number3,
               fontSize = 40.sp,
               fontWeight = FontWeight.W400,
           )
       }
    }
}
