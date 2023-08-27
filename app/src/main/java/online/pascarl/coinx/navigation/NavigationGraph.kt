package online.pascarl.coinx.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import online.pascarl.coinx.SlideInAnimation
import online.pascarl.coinx.nav_drawer.BecomeMerchant
import online.pascarl.coinx.nav_drawer.ContactUs
import online.pascarl.coinx.nav_drawer.InviteFriends
import online.pascarl.coinx.nav_drawer.MerchantPaymentInformation
import online.pascarl.coinx.nav_drawer.Settings
import online.pascarl.coinx.nav_drawer.SettingsViewModel
import online.pascarl.coinx.screens.*
import online.pascarl.coinx.screens.auth_screen.EmailResetConfirmation
import online.pascarl.coinx.screens.auth_screen.OtpScreen
import online.pascarl.coinx.screens.auth_screen.RegisterScreen
import online.pascarl.coinx.screens.auth_screen.ResetPassword
import online.pascarl.coinx.screens.auth_screen.UpdatePassword
import online.pascarl.coinx.screens.bottom_bar_navigation.*
import online.pascarl.coinx.screens.buyOrSell.BuyAmountScreen
import online.pascarl.coinx.screens.buyOrSell.BuyConfirmationScreen
import online.pascarl.coinx.screens.buyOrSell.BuyOrSellCryptos
import online.pascarl.coinx.screens.buyOrSell.SellAmountScreen
import online.pascarl.coinx.screens.buyOrSell.BuyOrSellSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.BuyOrderCreationScreen
import online.pascarl.coinx.screens.buyOrSell.ReleasingScreen
import online.pascarl.coinx.screens.buyOrSell.SellConfirmationScreen
import online.pascarl.coinx.screens.buyOrSell.SellOrderCreationScreen
import online.pascarl.coinx.screens.buyOrSell.TransferMoneyScreen
import online.pascarl.coinx.screens.releases.ComingSoon
import online.pascarl.spx.screens.CreateAccount


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController) {
    val sharedViewModel: BuyOrSellSharedViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()
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
        composable(route = Screen.Settings.route) {
            Settings(navController = navController)
        }

        composable(route = Screen.ContactUs.route) {
            ContactUs(navController = navController)
        }
        composable(route = Screen.InviteFriends.route) {
            InviteFriends(navController = navController)
        }
        composable(route = Screen.BecomeMerchant.route) {
            BecomeMerchant(navController = navController)
        }
        composable(route = Screen.MerchantPaymentInformation.route) {
            MerchantPaymentInformation(navController = navController)
        }



        navigation(
            startDestination = Screen.BuyOrSellCryptos.route,
            route = "buy_or_sell"
        ) {
            composable(route = Screen.BuyOrSellCryptos.route) {
                BuyOrSellCryptos(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.BuyAmountScreen.route) {
                BuyAmountScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.SellAmountScreen.route) {
                SellAmountScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.BuyConfirmationScreen.route) {
                BuyConfirmationScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.SellConfirmationScreen.route) {
                SellConfirmationScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.BuyOrderCreationScreen.route) {
                BuyOrderCreationScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.SellOrderCreationScreen.route) {
                SellOrderCreationScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.TransferMoneyScreen.route) {
                TransferMoneyScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(route = Screen.ReleasingScreen.route) {
                ReleasingScreen(navController = navController, sharedViewModel = sharedViewModel)
            }

        }

        composable(route = Screen.ComingSoon.route){
            ComingSoon(navController = navController)
        }


    }

}

const val AUTHENTICATION_ROUTE = "AUTH"
const val HOME_ROUTE = "HOME"
