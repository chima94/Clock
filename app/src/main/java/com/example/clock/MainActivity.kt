package com.example.clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.clock.navigation.addBottomNavigationDestions
import com.example.clock.navigation.bottomnavigation.AlarmRoute
import com.example.clock.navigation.bottomnavigation.AppBottomNavigation
import com.example.clock.ui.theme.ClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainHost()
                }
            }
        }
    }
}


@Composable
private fun MainHost(){
    val navController = rememberNavController()
    
    
    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                navHostController = navController
            )
        }
    ){
        NavHost(
            navController = navController,
            startDestination = AlarmRoute.route,
            builder = {
                addBottomNavigationDestions()
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
}