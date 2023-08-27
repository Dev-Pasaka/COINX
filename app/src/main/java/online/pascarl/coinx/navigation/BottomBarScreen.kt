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
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = Screen.Dashboard.route,
        title = "Home",
        icon = Icons.Default.Home
    )
    object Feed : BottomBarScreen(
        route = Screen.Feed.route,
        title = "Feed",
        icon = Icons.Default.Newspaper
    )
    object Swap : BottomBarScreen(
        route = Screen.Swap.route,
        title = "",
        icon = Icons.Default.CompareArrows
    )
    object Orders : BottomBarScreen(
        route = Screen.Orders.route,
        title = "Orders",
        icon = Icons.Default.Receipt
    )
    object Wallet : BottomBarScreen(
        route = Screen.Wallet.route,
        title = "Wallet",
        icon = Icons.Default.AccountBalanceWallet
    )

}

data class Swap(
    val icon: ImageVector  = Icons.Default.CompareArrows,
    val route:String = Screen.Swap.route,
)
