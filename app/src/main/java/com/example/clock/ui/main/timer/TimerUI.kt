package com.example.clock.ui.main.timer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.clock.R
import com.example.clock.ui.component.AppBar
import kotlinx.coroutines.delay
import timber.log.Timber
import java.lang.Math.PI
import kotlin.math.sin


@Composable
fun TimerUI(){

    var hour by remember{ mutableStateOf("00")}
    var minutes by remember{ mutableStateOf("00")}
    var seconds by remember{ mutableStateOf("00")}
    val time by remember{ mutableStateOf(hour + minutes + seconds)}
    val c by remember { mutableStateOf(time.toCharArray())}
    val c2 by remember{ mutableStateOf(ArrayList<Char>())}
    var counter by remember{ mutableStateOf(c.size - 1)}
    var showTimerPlayBtn by remember{ mutableStateOf(false)}
    var startTimer by remember{ mutableStateOf(false)}
    var isTimerRunning by remember{ mutableStateOf(false)}
    var k by remember{ mutableStateOf(5)}


    TimerUIScaffold(
        hour = hour,
        minutes = minutes,
        seconds = seconds,
        showTimerPlayBtn = showTimerPlayBtn,
        startTimer = startTimer,
        isTimerRunning = isTimerRunning
    ){action ->
        when(action){
            is TimerActions.NumberClicked ->{
                if(c2.size < 6){
                    val currentNumber = action.number.single()
                    c2.add(currentNumber)
                    if(c2[0] != '0'){
                        if(counter < 5){
                            var k = counter
                            for(i in 0 until c2.size){
                                c[k] = c2[i]
                                k += 1
                            }
                        }else{
                            c[counter] = currentNumber
                            showTimerPlayBtn = true
                        }
                        hour = String(c).substring(0, 2)
                        minutes = String(c).substring(2, 4)
                        seconds = String(c).substring(4, 6)
                        counter -= 1
                    }else{
                        c2.clear()
                    }
                }
            }
            TimerActions.ClearTimer ->{
                if(c2.isNotEmpty()){
                    k -= 1
                    counter += 1
                    c2.removeAt(c2.size - 1)
                    c[counter] = '0'
                    hour = String(c).substring(0, 2)
                    minutes = String(c).substring(2, 4)
                    seconds = String(c).substring(4, 6)
                    if(k < 0){
                        showTimerPlayBtn = false
                        k = 5
                    }
                }
            }
            TimerActions.StartTimer ->{
                if(!startTimer && !isTimerRunning){
                    startTimer = !startTimer
                    isTimerRunning = !isTimerRunning
                }else{
                    isTimerRunning = !isTimerRunning
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
    showTimerPlayBtn: Boolean,
    startTimer: Boolean,
    isTimerRunning: Boolean,
    action : (TimerActions) -> Unit,
){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.timer_topbar))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                if(!startTimer){
                    TimerContent(
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth(),
                        hour = hour,
                        minutes = minutes,
                        seconds = seconds,
                        action = action,
                        showTimerPlayBtn = showTimerPlayBtn
                    )
                }else{
                    Timer(
                        totalTime = 100L * 1000L,
                        handleColor = MaterialTheme.colors.primary,
                        inactiveBarColor = Color.DarkGray,
                        activeBarColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.Center),
                        isTimerRunning = isTimerRunning
                    )
                }
                if(showTimerPlayBtn){
                    StartTimer(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 74.dp),
                        isTimerRunning = isTimerRunning,
                        action = action
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
    showTimerPlayBtn: Boolean
){
    Column(
        modifier = modifier
    ) {
        TimerRow(
            hour = hour,
            minutes = minutes,
            seconds = seconds,
            startTimer = showTimerPlayBtn,
            action = action
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
    startTimer: Boolean,
    action: (TimerActions) -> Unit
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
            onClick = {
                action(TimerActions.ClearTimer)
            },
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
private fun StartTimer(
    modifier: Modifier = Modifier,
    isTimerRunning: Boolean,
    action: (TimerActions) -> Unit
){
    FloatingActionButton(
        onClick = {
            action(TimerActions.StartTimer)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isTimerRunning) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
            contentDescription = "start"
        )
    }
}


@Composable
private fun Timer(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
    isTimerRunning: Boolean,
){
    var size by remember{ mutableStateOf(IntSize.Zero)}
    var value by remember{ mutableStateOf(initialValue)}
    var currentTime by remember{ mutableStateOf(totalTime)}
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning){
        if(currentTime > 0 && isTimerRunning){
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size  = it
            }
    ){
        Canvas(modifier = modifier){
            drawArc(
                color = inactiveBarColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = 270f,
                sweepAngle = 360f * value,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (360 * value + 270f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = kotlin.math.cos(beta) * r
            val b = sin(beta) * r

            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        Text(
            text = (currentTime / 1000L).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}















