package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import online.pascarl.coinx.BottomUpAnimation
import online.pascarl.coinx.screens.SeeAllCryptos
import online.pascarl.coinx.screens.bottom_bar_navigation.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Dashboard.route ) {
        composable(route = Screen.Dashboard.route) {
            //Dashboard(navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            Profile(navController = navController)
        }
        composable(route = BottomBarScreen.Feed.route) {
            NewsFeed(navController = navController)
        }
        composable(route = BottomBarScreen.Wallet.route) {
            Wallet(navController = navController)
        }
        composable(route = BottomBarScreen.Swap.route) {
            Swap(navController = navController)
        }

        composable(route = Screen.SeeAllCryptos.route){
            BottomUpAnimation {
                SeeAllCryptos(navController = navController)
            }
        }
    }
}