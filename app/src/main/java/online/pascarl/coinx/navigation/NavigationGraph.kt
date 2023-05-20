package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import online.pascarl.coinx.BottomUpAnimation
import online.pascarl.coinx.SlideInAnimation
import online.pascarl.coinx.screens.*
import online.pascarl.spx.screens.CreateAccount


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController){



   NavHost(navController = navController, startDestination = Screen.SplashScreen.route ){
        composable(route = Screen.SplashScreen.route){
            SlideInAnimation {
                AnimatedSplashScreen(navController=navController)
            }
        }
       composable(route = Screen.SeeAllCryptos.route){
           BottomUpAnimation {
               SeeAllCryptos(navController = navController)
           }
       }
        composable(route = Screen.Register.route){
            SlideInAnimation {
                RegisterScreen(navController = navController)
            }

        }
        composable(route = Screen.CreateAccount.route){
            SlideInAnimation {
                CreateAccount(navController = navController)
            }
        }
        composable(route = Screen.Dashboard.route){
            SlideInAnimation {
                Dashboard(navController = navController)
            }
        }
       composable(route = Screen.ResetPassword.route){
           SlideInAnimation {
               ResetPassword(navController = navController)
           }
       }
       composable(route = Screen.EmailResetConfirmation.route){
           SlideInAnimation {
               EmailResetConfirmation(navController = navController)
           }
       }


    }

}