package com.example.clock.ui.main.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clock.R
import com.example.clock.ui.component.AppBar


@Composable
fun TimerUI(){
    var hour by remember{ mutableStateOf("00")}
    var minutes by remember{ mutableStateOf("00")}
    var seconds by remember{ mutableStateOf("00")}
    val time by remember{ mutableStateOf(hour + minutes + seconds)}
    val c by remember { mutableStateOf(time.toCharArray())}
    val c2 by remember{ mutableStateOf(ArrayList<Char>())}
    var counter by remember{ mutableStateOf(c.size - 1)}
    var startTimer by remember{ mutableStateOf(false)}


    TimerUIScaffold(
        hour = hour,
        minutes = minutes,
        seconds = seconds,
        startTimer = startTimer
    ){action ->
        when(action){
            is TimerActions.NumberClicked ->{
                if(c2.size < 6){
                    val currentNumber = action.number.single()
                    c2.add(currentNumber)
                    if(counter < 5){
                        var k = counter
                        for(i in 0 until c2.size){
                            c[k] = c2[i]
                            k += 1
                        }
                    }else{
                        c[counter] = currentNumber
                        startTimer = true
                    }
                    hour = String(c).substring(0, 2)
                    minutes = String(c).substring(2, 4)
                    seconds = String(c).substring(4, 6)
                    counter -= 1
                }
            }
        }
    }
}

@Composable
private fun TimerUIScaffold(
    hour: String,
    minutes: String,
    seconds: String,
    startTimer: Boolean,
    action : (TimerActions) -> Unit,
){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.timer_topbar))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                TimerContent(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth(),
                    hour = hour,
                    minutes = minutes,
                    seconds = seconds,
                    action = action,
                    startTimer = startTimer
                )
                if(startTimer){
                    StartTimer(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 74.dp)
                    )
                }
            }
        }
    )
}


@Composable
private fun TimerContent(
    modifier : Modifier,
    action: (TimerActions) -> Unit,
    hour: String,
    minutes: String,
    seconds: String,
    startTimer: Boolean
){
    Column(
        modifier = modifier
    ) {
        TimerRow(
            hour = hour,
            minutes = minutes,
            seconds = seconds,
            startTimer = startTimer
        )
        Spacer(modifier = Modifier.height(40.dp))
        Divider(modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .height(1.dp)
        )
        KeyBoardRow(
            number1 = "1",
            number2 = "2",
            number3 = "3",
            action = action
        )
        KeyBoardRow(
            number1 = "4",
            number2 = "5",
            number3 = "6",
            action = action
        )
        KeyBoardRow(
            number1 = "7",
            number2 = "8",
            number3 = "9",
            action = action
        )
        Row(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            TextButton(
                onClick = { action(TimerActions.NumberClicked("0"))}
            ) {
                Text(
                    text = "0",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
private fun TimerRow(
    hour: String,
    minutes: String,
    seconds: String,
    startTimer: Boolean
){
    val color = if(startTimer) MaterialTheme.colors.primary else Color.Unspecified
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        TimeRow(
            text1 = hour,
            text2 = "h",
            color = color
        )
        TimeRow(
            text1 = minutes,
            text2 = "m",
            color = color
        )
        TimeRow(
            text1 = seconds,
            text2 = "s",
            color = color
        )
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
private fun TimeRow(
    text1: String,
    text2: String,
    color: Color
){
    Row(modifier = Modifier.padding(8.dp)){
        Text(
            text = text1,
            fontSize = 50.sp,
            modifier = Modifier
                .padding(4.dp)
                .alignByBaseline(),
            color = color
        )
        Text(
            text = text2,
            fontSize = 22.sp,
            modifier = Modifier.alignByBaseline(),
            color = color
        )
    }
}


@Composable
private fun KeyBoardRow(
    number1: String,
    number2: String,
    number3: String,
    action: (TimerActions) -> Unit
){
    Row(
        modifier = Modifier
            .padding(top = 35.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        TextButton(
            onClick = {
                action(TimerActions.NumberClicked(number1))
            }
        ) {
            Text(
                text = number1,
                fontSize = 40.sp,
                fontWeight = FontWeight.W400,
                color = Color.White
            )
        }
        TextButton(
            onClick = {
                action(TimerActions.NumberClicked(number2))
            }
        ) {
            Text(
                text = number2,
                fontSize = 40.sp,
                fontWeight = FontWeight.W400,
                color = Color.White
            )
        }
       TextButton(
           onClick = { action(TimerActions.NumberClicked(number3))   }
       ) {
           Text(
               text = number3,
               fontSize = 40.sp,
               fontWeight = FontWeight.W400,
               color = Color.White
           )
       }
    }
}


@Composable
private fun StartTimer(modifier: Modifier = Modifier){
    FloatingActionButton(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.PlayArrow,
            contentDescription = "start"
        )
    }
}