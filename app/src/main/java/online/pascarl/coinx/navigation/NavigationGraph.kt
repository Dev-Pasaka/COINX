package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import online.pascarl.coinx.screens.Dashboard
import online.pascarl.coinx.screens.RegisterScreen
import online.pascarl.coinx.screens.AnimatedSplashScreen
import online.pascarl.coinx.screens.NoInternet
import online.pascarl.spx.screens.CreateAccount


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController){


    NavHost(navController = navController, startDestination = Screen.SplashScreen.route ){
        composable(route = Screen.SplashScreen.route){
            AnimatedSplashScreen(navController=navController)
        }
        composable(route = Screen.Register.route){
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.CreateAccount.route){
            CreateAccount(navController = navController)
        }
        composable(route = Screen.Dashboard.route){
            Dashboard(navController = navController)
        }

    }

}