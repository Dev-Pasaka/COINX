package online.pascarl.coinx.navigation

import android.graphics.drawable.Icon
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable

import androidx.compose.material.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun CustomBottomNavigation(navController: NavHostController) {

    val bottomScreens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Feed,
        BottomBarScreen.Swap,
        BottomBarScreen.Orders,
        BottomBarScreen.Wallet,
    )
    NavigationBar(
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        containerColor = MaterialTheme.colorScheme.surface

    ){
            bottomScreens.forEachIndexed{
                index, item ->
                NavigationBarItem(
                    label = {
                            Text(
                                text = bottomScreens[index].title,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    },
                    icon = {
                        Icon(
                            imageVector = bottomScreens[index].icon,
                            contentDescription = bottomScreens[index].title,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    selected = SELECTEDITEM == index,
                    onClick = {
                        navController.navigate(bottomScreens[index].route){
                            SELECTEDITEM = index
                            popUpTo(bottomScreens[index].route){inclusive = true}
                        }
                    }
                )
            }
    }
}
var SELECTEDITEM = 0