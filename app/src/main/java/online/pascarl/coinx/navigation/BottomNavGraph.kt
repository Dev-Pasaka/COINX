package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {


    }
}