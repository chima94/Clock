package com.example.clock.ui.main.bedtime

import android.animation.ValueAnimator
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.compose.*
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
            Box(modifier = Modifier.fillMaxSize()){
                BedTimeContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .align(Alignment.TopCenter)
                )
                Animation(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )

                GetStarted(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp)
                )
            }
        }
    )
}


@Composable
private fun BedTimeContent(modifier: Modifier){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Bedtime,
            contentDescription = "BedTime",
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = "Set a consistent bedtime for better sleep",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(top = 14.dp)
        )
        Text(
            text = "Choose a regular bedtime, disconnect from your phone, and listen to " +
                    "soothing sounds",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
private fun GetStarted(modifier: Modifier){
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Text(text = "Get started")
    }
}


@Composable
private fun Animation(modifier: Modifier){
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("night.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    Box(modifier = modifier) {
        LottieAnimation(
            composition,
            progress
        )
    }
}


