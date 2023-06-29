package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.composable
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
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.Register.route) {
            SlideInAnimation {
                RegisterScreen(navController = navController)
            }
        }
        composable(route = Screen.CreateAccount.route) {
            SlideInAnimation {
                CreateAccount(navController = navController)
            }
        }
        composable(route = Screen.ResetPassword.route) {
            SlideInAnimation {
                ResetPassword(navController = navController)
            }
        }
        composable(route = Screen.EmailResetConfirmation.route) {
            SlideInAnimation {
                EmailResetConfirmation(navController = navController)
            }
        }
        composable(route = Screen.OtpScreen.route) {
            SlideInAnimation {
                OtpScreen(navController = navController)
            }
        }
        composable(route = Screen.UpdatePasswordScreen.route) {
            SlideInAnimation {
                UpdatePassword(navController = navController)
            }
        }
        composable(route = Screen.NavigationDrawer.route) {
            SlideInAnimation {
                NavDrawer(navController = navController)
            }
        }



        composable(route = Screen.Dashboard.route) {
            Dashboard(navController = navController)

        }
        composable(route = Screen.Orders.route) {
            Orders(navController = navController)

        }
        composable(route = Screen.Feed.route) {
            NewsFeed(navController = navController)
        }
        composable(route = Screen.Wallet.route) {
            Wallet(navController = navController)

        }
        composable(route = Screen.Swap.route) {
            Swap(navController = navController)

        }

        composable(route = Screen.SeeAllCryptos.route) {
            SeeAllCryptos(navController = navController)
        }


        composable(route = Screen.BuyOrSellCryptos.route){
            BuyOrSellCryptos(navController = navController)
        }

    }

}

const val AUTHENTICATION_ROUTE = "AUTH"
const val HOME_ROUTE = "HOME"
