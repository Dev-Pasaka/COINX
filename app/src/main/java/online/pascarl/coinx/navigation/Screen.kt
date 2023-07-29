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
    object BuyAmountScreen: Screen("buy_amount_screen")
    object SellAmountScreen: Screen("sell_amount_screen")
    object BuyConfirmationScreen:Screen("buy_confirmation_screen")
    object SellConfirmationScreen:Screen("sell_confirmation_screen")
    object BuyOrderCreationScreen:Screen("buy_order_creation_screen")
    object TransferMoneyScreen:Screen("transfer_amount_screen")
    object ReleasingScreen:Screen("Releasing_screen")
    object SellOrderCreationScreen:Screen("sell_order_creation_screen")
    object Settings:Screen("settings")
    object ContactUs:Screen("contact_us")
    object InviteFriends:Screen("invite_friends")




}
