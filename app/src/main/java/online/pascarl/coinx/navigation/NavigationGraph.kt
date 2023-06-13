package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.SlideInAnimation
import online.pascarl.coinx.screens.*
import online.pascarl.coinx.screens.auth_screen.EmailResetConfirmation
import online.pascarl.coinx.screens.auth_screen.OtpScreen
import online.pascarl.coinx.screens.auth_screen.RegisterScreen
import online.pascarl.coinx.screens.auth_screen.ResetPassword
import online.pascarl.coinx.screens.auth_screen.UpdatePassword
import online.pascarl.coinx.screens.bottom_bar_navigation.*
import online.pascarl.spx.screens.CreateAccount


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController){


    val bottomNavController = rememberNavController()

   NavHost(navController = navController, startDestination = Screen.SplashScreen.route ){
        composable(route = Screen.SplashScreen.route){
            AnimatedSplashScreen(navController=navController)
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
       composable(route = Screen.OtpScreen.route){
           SlideInAnimation {
               OtpScreen(navController = navController)
           }
       }
       composable(route = Screen.UpdatePasswordScreen.route){
           SlideInAnimation {
               UpdatePassword(navController = navController)
           }
       }
       composable(route = Screen.BottomBarNavigationContainer.route){
           SlideInAnimation {
               BottomBarNavigationContainer(navController = bottomNavController)
           }
       }




    }

}
