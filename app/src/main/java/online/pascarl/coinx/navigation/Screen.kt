package online.pascarl.coinx.navigation

sealed class Screen(val route:String){
    object SplashScreen: Screen("splash_screen")
    object Register: Screen("registerAndSign_screen")
    object CreateAccount: Screen("create_account")
    object Dashboard: Screen("dashboard")
    object Feed: Screen("feed")
    object Swap: Screen("swap")
    object Orders: Screen("orders")
    object Wallet: Screen("wallet")
    object NoInternet: Screen("no_internet")
    object SeeAllCryptos: Screen("see_all_cryptos")
    object ResetPassword: Screen("ResetPassword")
    object OtpScreen: Screen("otp_screen")
    object UpdatePasswordScreen: Screen("update_password")
    object EmailResetConfirmation: Screen("EmailResetConfirmation")
    object NavigationDrawer: Screen("nav_drawer")
    object BuyOrSellCryptos: Screen("buy_or_sell_cryptos")


}
