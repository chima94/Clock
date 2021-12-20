package com.example.clock.ui.main.alarm

import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateFormat
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.example.clock.R
import com.example.clock.business.datasources.alarm.AlarmEntity
import com.example.clock.business.model.SetAlarm
import com.example.clock.business.model.toAlarmEntity
import com.example.clock.business.model.toSetAlarm
import com.example.clock.ui.component.AppBar
import timber.log.Timber
import java.util.*


@Composable
fun AlarmUI(){
    
    val alarmViewModel: AlarmViewModel = hiltViewModel()
    var openDialog by remember{ mutableStateOf(false)}
    val lifecycleOwner = LocalLifecycleOwner.current
    val locationFlowLifecycleAware = remember(alarmViewModel.alarm, lifecycleOwner) {
        alarmViewModel.alarm.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val alarmEntity by locationFlowLifecycleAware.collectAsState(initial = emptyList())
    var alarm by remember { mutableStateOf(SetAlarm())}

    AlarmUIScaffold(openDialog, alarmEntity = alarmEntity){ action ->
        when(action){
            is AlarmUIActions.OpenDialog ->{
                openDialog = !openDialog
                if(action.alarmEntity != null){
                    alarm =  action.alarmEntity.toSetAlarm()
                }
                if(action.label.isNotEmpty()){
                    alarm.label = action.label
                    alarmViewModel.insertAlarm(alarm.toAlarmEntity())
                }
            }
            is AlarmUIActions.TurnOf_On_Alarm ->{
                val alarm = SetAlarm(
                    millis = action.millis,
                    timeState = action.am_pm,
                    isSetAlarm = true,
                    repeat = alarmViewModel.weekdays()
                )
                alarmViewModel.insertAlarm(alarm.toAlarmEntity())
            }
            is AlarmUIActions.UpdateAlarm ->{
                alarmViewModel.insertAlarm(action.alarmEntity)
            }
            is AlarmUIActions.DeleteAlarm ->{
                alarmViewModel.deleteAlarm(action.millis)
            }
            is AlarmUIActions.SetAlarm ->{
                alarmViewModel.setAlarm(action.alarmEntity.millis)
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
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)){
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 120.dp)
                    ) {
                        items(alarmEntity){alarm->
                            Alarm(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                alarmEntity = alarm,
                                action = action
                            )
                        }
                    }
                LabelDialog(openDialog = openDialog, action = action)
                SetTime(
                    modifier = Modifier
                        .padding(bottom = 74.dp)
                        .align(Alignment.BottomCenter),
                    action = action
                )
                EmptyScreen(
                    alarmEntity = alarmEntity,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}


@Composable
private fun EmptyScreen(alarmEntity: List<AlarmEntity>, modifier: Modifier = Modifier){
    if(alarmEntity.isEmpty()){
        Box(modifier = modifier){
            Icon(
                imageVector = Icons.Outlined.Alarm,
                contentDescription = "Empty",
                modifier = Modifier.size(200.dp),
                tint = Color.Gray
            )
        }
    }
}



@Composable
private fun SetTime(
    modifier: Modifier = Modifier,
    action: (AlarmUIActions) -> Unit
){
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            setTimer(context = context){millis, am_pm ->
                action(AlarmUIActions.TurnOf_On_Alarm(millis, am_pm))
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
    var expanded by remember{ mutableStateOf(alarmEntity.expanded)}
    var alarmChecked by remember { mutableStateOf(alarmEntity.isSetAlarm)}
    var repeat by remember{ mutableStateOf(false)}
    val am_pm = if(alarmEntity.timeState == 0) "AM" else "PM"
    val label = alarmEntity.label.capitalize()
    val currentAlarm = if(alarmEntity.label.isNotEmpty()) "${currentTime(alarmEntity.millis)} . $label" else currentTime(alarmEntity.millis)
    val labelDropdown = label.ifEmpty { "Label" }
    val color = if(alarmChecked) MaterialTheme.colors.secondary else Color.Unspecified

    if(alarmChecked){
        action(AlarmUIActions.SetAlarm(alarmEntity))
    }

    Card(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column(Modifier
            .clickable {
            expanded = !expanded
            if(alarmEntity.expanded){
                alarmEntity.expanded = expanded
                action(AlarmUIActions.UpdateAlarm(alarmEntity))
            }
            }
        ) {
                Box(modifier = modifier) {
                    Row {
                        Text(
                            text = convertDate(alarmEntity.millis),
                            fontSize = 50.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .alignByBaseline(),
                            color = color
                        )
                        Text(
                            text = am_pm,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .alignByBaseline(),
                            color = color
                        )
                    }
                   Switch(
                       checked = alarmChecked,
                       onCheckedChange = {
                           alarmChecked = !alarmChecked
                           alarmEntity.isSetAlarm = alarmChecked
                           action(AlarmUIActions.UpdateAlarm(alarmEntity = alarmEntity))
                       },
                       modifier = Modifier.align(Alignment.CenterEnd)
                   )
                }
            if(!expanded){
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.padding(top = 12.dp, start = 8.dp, bottom = 8.dp)
                ){
                    Text(
                        text = currentAlarm,
                        fontWeight = FontWeight.W500
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "dropdown"
                    )
                }
            }

            AnimatedVisibility(expanded) {
                Column(modifier = modifier.padding(top = 4.dp)){
                    AlarmExpandRow {
                        Checkbox(
                            checked = repeat,
                            onCheckedChange = { repeat = it },
                            modifier = Modifier.padding(start = 14.dp)
                        )
                        Text(
                            text = "Repeat",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 12.dp)
                        )
                    }
                    WeekDaysRow(checkBox = repeat, alarmEntity = alarmEntity)

                    Box(modifier = modifier) {
                        AlarmExpandRow {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Outlined.NotificationsActive,
                                    contentDescription = "ringtone",
                                )
                            }
                            Text(
                                text = "Argon",
                                modifier = Modifier
                                    .padding(top = 12.dp)
                            )
                        }
                        Row(modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.CenterEnd)
                        ) {
                            Checkbox(
                                checked = repeat,
                                onCheckedChange = { repeat = it },
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Vibrate",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    AlarmExpandRow {
                        IconButton(
                            onClick = {
                                action(AlarmUIActions.OpenDialog(alarmEntity = alarmEntity))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Label,
                                contentDescription = "label",

                            )
                        }
                        Text(
                            text = labelDropdown,
                            modifier = Modifier
                                .clickable { action(AlarmUIActions.OpenDialog(alarmEntity = alarmEntity)) }
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Box(modifier = modifier) {
                        AlarmExpandRow {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.BubbleChart,
                                    contentDescription = "Google Assistant Routine",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Text(
                                text = "Google Assistant Routine",
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.AddCircleOutline,
                            contentDescription = "add",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 4.dp, bottom = 4.dp)
                        )
                    }

                    Divider(
                        Modifier
                            .padding(4.dp)
                            .height(1.dp)
                    )


                    Box(modifier = modifier.padding(bottom = 16.dp)) {
                        AlarmExpandRow {
                           IconButton(
                               onClick = {
                                   action(AlarmUIActions.DeleteAlarm(alarmEntity.millis))
                               }
                           ) {
                               Icon(
                                   imageVector = Icons.Filled.Delete,
                                   contentDescription = "Delete",
                                   modifier = Modifier.padding(8.dp)
                               )
                           }
                            Text(
                                text = "Delete",
                                modifier = Modifier
                                    .padding(top = 14.dp)
                                    .clickable { action(AlarmUIActions.DeleteAlarm(alarmEntity.millis)) }

                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowUp,
                            contentDescription = "up",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(top = 12.dp)
                        )
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
private fun WeekDaysRow(alarmEntity: AlarmEntity, checkBox: Boolean){
    AnimatedVisibility(checkBox) {
        LazyRow(modifier = Modifier.padding(top = 4.dp, start = 8.dp)) {
            items(alarmEntity.repeat){weekdays ->
                WeekDays(day = weekdays.day)
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

private fun currentTime(timeInMillis: Long): String{
    return if(System.currentTimeMillis() < timeInMillis){
        "Today"
    }else{
        "Tomorrow"
    }
}