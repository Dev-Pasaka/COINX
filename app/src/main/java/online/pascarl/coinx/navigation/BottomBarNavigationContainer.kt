package online.pascarl.coinx.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.R
import online.pascarl.coinx.screens.bottom_bar_navigation.*


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarNavigationContainer(navController:NavHostController){
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Bottom,
      modifier = Modifier.fillMaxHeight()
  ){
      Scaffold(
          bottomBar = { BottomBarNavigation(navController = navController) }
      ){
          BottomNavGraph(navController = navController)

      }
  }

}


@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val bottomBarScreenItems = bottomNavigationItems
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.background)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomBarScreenItems.forEach {item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = "Icon") },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) }
            )

        }

    }
}