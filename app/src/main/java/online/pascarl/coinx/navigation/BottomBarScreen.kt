package online.pascarl.coinx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Home:BottomBarScreen(
        route = "dashboard",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Profile:BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
    object Wallet:BottomBarScreen(
        route = "wallet",
        title = "Wallet",
        icon = Icons.Default.Wallet
    )
    object Swap:BottomBarScreen(
        route = "swap",
        title = "Swap",
        icon = Icons.Default.CompareArrows
    )
    object Feed:BottomBarScreen(
        route = "feed",
        title = "Feed",
        icon = Icons.Default.Newspaper
    )


}

val bottomNavigationItems = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Feed,
    BottomBarScreen.Swap,
    BottomBarScreen.Profile,
    BottomBarScreen.Wallet
)