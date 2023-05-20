package online.pascarl.coinx.navigation

sealed class Screen(val route:String){
    object SplashScreen: Screen("splash_screen")
    object Register: Screen("registerAndSign_screen")
    object CreateAccount: Screen("create_account")
    object Dashboard: Screen("dashboard")
    object NoInternet: Screen("no_internet")
    object SeeAllCryptos: Screen("see_all_cryptos")
    object ResetPassword: Screen("ResetPassword")
    object EmailResetConfirmation: Screen("EmailResetConfirmation")


}
