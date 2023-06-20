package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.composable
import online.pascarl.coinx.SlideInAnimation
import online.pascarl.coinx.screens.bottom_bar_navigation.Dashboard
import online.pascarl.coinx.screens.bottom_bar_navigation.NewsFeed
import online.pascarl.coinx.screens.bottom_bar_navigation.Orders
import online.pascarl.coinx.screens.bottom_bar_navigation.Swap
import online.pascarl.coinx.screens.bottom_bar_navigation.Wallet

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {


    }
}