package com.example.clock.navigation.bottomnavigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun AppBottomNavigation(
    navHostController: NavHostController,
    hideBottomNavigation: List<String> = emptyList()
){
    var hideBottomNav by rememberSaveable { mutableStateOf(false)}
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val size = if(hideBottomNav){
        Modifier.size(animateDpAsState(targetValue = 0.dp, animationSpec = tween()).value)
    }else{
        Modifier
    }

    BottomNavigation(
        modifier = size.navigationBarsPadding(),
        backgroundColor = Color.White
    ) {
        hideBottomNav = currentRoute in hideBottomNavigation
        bottomNavigationEntries.forEach { bottomEntry -> 
            BottomNavigationItem(
                selected = currentRoute == bottomEntry.screen.route,
                alwaysShowLabel = true,
                onClick = {
                    navHostController.navigate(bottomEntry.screen.route){
                        restoreState = true
                        popUpTo(navHostController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(
                        modifier = Modifier.wrapContentSize(unbounded = true),
                        softWrap = true,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        text = stringResource(id = bottomEntry.screen.resourcesID),
                        color = if(currentRoute == bottomEntry.screen.route){
                            MaterialTheme.colors.primary
                        }else{
                            Color.Unspecified
                        }
                    )
                },
                icon = {
                    if(currentRoute == bottomEntry.screen.route){
                        Icon(
                            imageVector = bottomEntry.selectedIcon,
                            contentDescription = stringResource(id = bottomEntry.screen.resourcesID),
                            tint = if(currentRoute == bottomEntry.screen.route){
                                MaterialTheme.colors.primary
                            }else{
                                Color.Gray
                            }
                        )
                    }else{
                        Icon(
                            imageVector = bottomEntry.icon,
                            contentDescription = stringResource(id = bottomEntry.screen.resourcesID),
                            tint = if(currentRoute == bottomEntry.screen.route){
                                MaterialTheme.colors.primary
                            }else{
                                Color.Gray
                            }
                        )
                    }
                }

            )
        }
    }
}