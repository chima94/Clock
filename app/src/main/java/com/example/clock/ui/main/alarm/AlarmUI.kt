package com.example.clock.ui.main.alarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clock.R
import com.example.clock.ui.component.AppBar


@Composable
fun AlarmUI(){

    AlarmUIScaffold()
}


@Composable
private fun AlarmUIScaffold(){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.topbar_alarm))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
               SetTime(
                   modifier = Modifier
                       .align(Alignment.BottomCenter)
                       .padding(bottom = 74.dp)
               )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                ) {
                    Alarm(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    )
}

@Composable
private fun SetTime(modifier: Modifier){
    FloatingActionButton(
        onClick = { /*TODO*/ },
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
private fun Alarm(modifier: Modifier){
    var expanded by remember{ mutableStateOf(false)}
    var checked by remember { mutableStateOf(false)}
    var checkBox by remember{ mutableStateOf(false)}
    Card(
        modifier = modifier,
        elevation = 0.dp
    ) {
        Column(Modifier.clickable { expanded = !expanded }) {
                Box(modifier = modifier) {
                    Row {
                        Text(
                            text = "9:49",
                            fontSize = 50.sp,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .alignByBaseline()
                        )
                        Text(
                            text = "PM",
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
                    modifier = modifier.padding(top = 16.dp)
                ){
                    Text(text = "Tomorrow")
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "dropdown"
                    )
                }
            }
            AnimatedVisibility(expanded) {
                LazyColumn(modifier = modifier.padding(top = 4.dp)){
                    item {
                        AlarmExpandRow {
                            Checkbox(
                                checked = checkBox,
                                onCheckedChange = {checkBox = it},
                                modifier = Modifier.padding( 8.dp)
                            )
                            Text(
                                text = "Repeat",
                                modifier = Modifier.padding( 8.dp)
                            )
                        }
                    }
                    item{
                       Box(modifier = modifier){
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
                           Row(modifier = Modifier.align(Alignment.CenterEnd)){
                               Checkbox(
                                   checked = checkBox,
                                   onCheckedChange = {checkBox = it},
                                   modifier = Modifier.padding( 8.dp)
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
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Label",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                    item {
                        Box(modifier = modifier){
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
                    item { Divider(
                        Modifier
                            .padding(4.dp)
                            .height(1.dp)) }
                    item {
                        Box(modifier = modifier){
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
     content: @Composable () ->Unit
){
    Row(modifier = Modifier.padding(top = 4.dp)) {
        content()
    }
}