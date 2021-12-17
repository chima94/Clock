package com.example.clock.ui.main.clock

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clock.R
import com.example.clock.ui.component.AppBar

@Composable
fun ClockUI(){

   ClockUIScaffold()
}

@Composable
private fun ClockUIScaffold(){
    Scaffold(
        topBar = {
            AppBar(topAppBarText = stringResource(R.string.clock_topbar))
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()){
                Search(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 74.dp)
                )
                ClockUIContent(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Composable
private fun ClockUIContent(modifier: Modifier){
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "11:52",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .alignByBaseline()
                )
                Text(
                    text = "PM",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .alignByBaseline()
                )
            }
        }

       item {
           Column {
               Text(
                   text = "Tue,Dec 14",
                   fontWeight = FontWeight.W400,
                   fontSize = 18.sp,
                   textAlign = TextAlign.Center,
                   modifier = modifier
               )

               Divider(
                   modifier = modifier
                       .padding(8.dp)
                       .height(IntrinsicSize.Max)
               )
           }
       }

        items(worldClock){clockState ->
            ClockRow(
                clockState = clockState,
                modifier = modifier
            )
        }

    }

}



@Composable
private fun Search(
    modifier: Modifier
){
    FloatingActionButton(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Language,
            contentDescription = "search worldwide"
        )
    }
}




@Composable
private fun ClockRow(
    clockState: ClockState,
    modifier: Modifier
){

        Row(
            modifier = modifier
                .padding(top = 20.dp, start = 8.dp, end = 8.dp)
        ){
            Column{
                Text(
                    text = clockState.country,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = clockState.timeAhead,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                )
            }
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .padding(end = 16.dp)
            ){
                Text(
                    text = clockState.time,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .alignByBaseline()
                )
                Text(
                    text = clockState.state,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier
                        .alignByBaseline(),
                )
            }
        }

    }



data class ClockState(val time: String, val country: String, val timeAhead: String, val state: String)

val worldClock = listOf(
    ClockState(
        "12:40",
        "Nigeria",
        "7 hours ahead",
        "PM"
    ),
    ClockState(
        "12:40",
        "Nigeria",
        "7 hours ahead",
        "PM"
    ),
    ClockState(
        "12:40",
        "Nigeria",
        "7 hours ahead",
        "PM"
    ),
    ClockState(
        "12:40",
        "Nigeria",
        "7 hours ahead",
        "PM"
    )
)