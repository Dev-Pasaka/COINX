package online.pascarl.coinx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val index:Int
) {
    object Home : BottomBarScreen(
        route = Screen.Dashboard.route,
        title = "Home",
        icon = Icons.Default.Home,
        index = 0
    )
    object Feed : BottomBarScreen(
        route = Screen.Feed.route,
        title = "Feed",
        icon = Icons.Default.Newspaper,
        index = 1
    )
    object Swap : BottomBarScreen(
        route = Screen.Swap.route,
        title = "",
        icon = Icons.Default.CompareArrows,
        index = 2

    )
    object Orders : BottomBarScreen(
        route = Screen.Orders.route,
        title = "Orders",
        icon = Icons.Default.Receipt,
        index = 3
    )
    object Wallet : BottomBarScreen(
        route = Screen.Wallet.route,
        title = "Wallet",
        icon = Icons.Default.AccountBalanceWallet,
        index = 4
    )

}

data class Swap(
    val icon: ImageVector  = Icons.Default.CompareArrows,
    val route:String = Screen.Swap.route,
)
