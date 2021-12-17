package com.example.clock.ui.main.alarm

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.util.Log.i
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.clock.R
import com.example.clock.business.datasources.alarm.AlarmEntity
import com.example.clock.business.model.SetAlarm
import com.example.clock.ui.component.AppBar
import timber.log.Timber
import timber.log.Timber.i
import java.util.*


@Composable
fun AlarmUI(){
    
    val alarmViewModel: AlarmViewModel = hiltViewModel()
    var openDialog by remember{ mutableStateOf(false)}
    val lifecycleOwner = LocalLifecycleOwner.current
    val locationFlowLifecycleAware = remember(alarmViewModel.alarm, lifecycleOwner) {
        alarmViewModel.alarm.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val alarm by locationFlowLifecycleAware.collectAsState(initial = emptyList())
    Timber.i("Size: ${alarm.size}")

    AlarmUIScaffold(openDialog, alarm){action ->
        when(action){
            is AlarmUIActions.OpenDialog ->{
                openDialog = !openDialog
            }
            is AlarmUIActions.SetAlarm ->{
                val alarm = SetAlarm(
                    millis = action.millis,
                    timeState = action.am_pm,
                    isSetAlarm = true,
                    repeat = alarmViewModel.weekdays()
                )
                alarmViewModel.insertAlarm(alarm)
            }
        }
    }
}


@Composable
private fun AlarmUIScaffold(
    openDialog: Boolean,
    alarmEntity: List<AlarmEntity>,
    action: (AlarmUIActions) -> Unit
) {

    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.topbar_alarm))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
               SetTime(
                   modifier = Modifier
                       .align(Alignment.BottomCenter)
                       .padding(bottom = 74.dp),
                   action = action
               )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ) {
                    alarmEntity.forEach { alarmEntity ->
                        Alarm(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            alarmEntity = alarmEntity,
                            action = action
                        )
                    }
                }
                LabelDialog(openDialog = openDialog, action = action)
            }
        }
    )
}





@Composable
private fun SetTime(
    modifier: Modifier,
    action: (AlarmUIActions) -> Unit
){
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            setTimer(context = context){millis, am_pm ->
                action(AlarmUIActions.SetAlarm(millis, am_pm))
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.set_alarm)
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Alarm(
    modifier: Modifier,
    alarmEntity: AlarmEntity,
    action: (AlarmUIActions) -> Unit
){
    var expanded by remember{ mutableStateOf(false)}
    var checked by remember { mutableStateOf(false)}
    var checkBox by remember{ mutableStateOf(false)}
    val am_pm = if(alarmEntity.timeState == 0) "AM" else "PM"

    Card(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column(Modifier.clickable { expanded = !expanded }) {
                Box(modifier = modifier) {
                    Row {
                        Text(
                            text = convertDate(alarmEntity.millis),
                            fontSize = 50.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .alignByBaseline()
                        )
                        Text(
                            text = am_pm,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .alignByBaseline()
                        )
                    }
                   Switch(
                       checked = checked,
                       onCheckedChange = {checked = it},
                       modifier = Modifier.align(Alignment.CenterEnd)
                   )
                }
            if(!expanded){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.padding(top = 12.dp, start = 8.dp, bottom = 8.dp)
                ){
                    Text(text = "Tomorrow")
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "dropdown"
                    )
                }
            }
            AnimatedVisibility(expanded) {
                    LazyColumn(modifier = modifier.padding(top = 4.dp)) {
                        item {
                            Column {
                                AlarmExpandRow {
                                    Checkbox(
                                        checked = checkBox,
                                        onCheckedChange = { checkBox = it },
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "Repeat",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                WeekDaysRow(checkBox = checkBox)
                            }
                        }
                        item {
                            Box(modifier = modifier) {
                                AlarmExpandRow {
                                    Icon(
                                        imageVector = Icons.Outlined.NotificationsActive,
                                        contentDescription = "ringtone",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "Argon",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                                    Checkbox(
                                        checked = checkBox,
                                        onCheckedChange = { checkBox = it },
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "Vibrate",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        item {
                            AlarmExpandRow {
                                Icon(
                                    imageVector = Icons.Outlined.Label,
                                    contentDescription = "label",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { action(AlarmUIActions.OpenDialog()) }
                                )
                                Text(
                                    text = "Label",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { action(AlarmUIActions.OpenDialog()) }
                                )
                            }
                        }
                        item {
                            Box(modifier = modifier) {
                                AlarmExpandRow {
                                    Icon(
                                        imageVector = Icons.Filled.BubbleChart,
                                        contentDescription = "Google Assistant Routine",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "Google Assistant Routine",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Outlined.AddCircleOutline,
                                    contentDescription = "add",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 4.dp)
                                )
                            }
                        }
                        item {
                            Divider(
                                Modifier
                                    .padding(4.dp)
                                    .height(1.dp)
                            )
                        }
                        item {
                            Box(modifier = modifier) {
                                AlarmExpandRow {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "Delete",
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Outlined.KeyboardArrowUp,
                                    contentDescription = "up",
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }
                }
            }
    }
    if(!expanded){
        Divider(
            Modifier
                .padding(top = 6.dp)
                .height(1.dp))
    }
}


@Composable
private fun AlarmExpandRow(
    modifier: Modifier = Modifier,
     content: @Composable () ->Unit,
){
    Row(modifier = Modifier.padding(top = 4.dp)) {
        content()
    }
}




@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun WeekDaysRow(checkBox: Boolean){
    AnimatedVisibility(checkBox) {
        LazyRow(modifier = Modifier.padding(top = 4.dp, start = 8.dp)) {
            items(weekdays){day ->
                WeekDays(day = day)
            }
        }
    }
}


@Composable
private fun WeekDays(day: String){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(30.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colors.primary)
            .clickable { }
    ){
        Text(
            text = day,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}



@Composable
private fun LabelDialog(
    openDialog: Boolean,
    action: (AlarmUIActions) -> Unit
){
    var text by remember{ mutableStateOf("")}
    if(openDialog){
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = {
                Text(
                    text = "Label",
                    modifier = Modifier.padding(8.dp)
                )
            },
            text = {
                OutlinedTextField(
                    value = text,
                    onValueChange = {text = it},
                    modifier = Modifier.padding(8.dp)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        action(AlarmUIActions.OpenDialog(text))
                    }
                ) {
                    Text(text = "ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        action(AlarmUIActions.OpenDialog(text))
                    }
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}


private fun setTimer(context: Context, callback: (millis: Long, Int)->Unit){
    Calendar.getInstance().apply {
        this.set(Calendar.SECOND, 0)
        this.set(Calendar.MILLISECOND, 0)
        TimePickerDialog(
            context,
            0,
            {_, hour, minute ->
                this.set(Calendar.HOUR_OF_DAY, hour)
                this.set(Calendar.MINUTE, minute)
                val am_pm = this.get(Calendar.AM_PM)
                callback(this.timeInMillis, am_pm)
            },
            this.get(Calendar.HOUR_OF_DAY),
            this.get(Calendar.MINUTE),
            false
        ).show()
    }
}

private fun convertDate(timeInMillis: Long): String = DateFormat.format("hh:mm", timeInMillis).toString()

private val weekdays = listOf("S", "M", "T", "W", "T", "F", "S")