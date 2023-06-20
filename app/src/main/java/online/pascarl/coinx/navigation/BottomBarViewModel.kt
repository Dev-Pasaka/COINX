package online.pascarl.coinx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.BottomBar


class BottomBarViewModel(): ViewModel(){

    val selectedIndex get() = SELECTEDINDEX
    private  var _route by mutableStateOf(Screen.Dashboard.route)
    val route get() = _route

    val bottomNavigationItems = mutableStateListOf(
        BottomBar( index = 0, title = "Home", icon = Icons.Filled.Home),
        BottomBar( index = 1,title = "Feed", icon = Icons.Filled.Newspaper),
        BottomBar( index = 2,title = "Swap", icon = Icons.Filled.CompareArrows),
        BottomBar( index = 3, title = "Orders", icon = Icons.Filled.Receipt),
        BottomBar( index = 4,title = "Wallet", icon = Icons.Filled.AccountBalanceWallet),
    )



    fun navigateTo(index:Int){
        SELECTEDINDEX = index
        when(selectedIndex){
            0 -> {
                SELECTEDINDEX = 0
                _route = Screen.Dashboard.route

            }
            1 -> {
                SELECTEDINDEX = 1
                _route = Screen.Feed.route
            }
            2-> {
                SELECTEDINDEX = 2
                _route = Screen.Swap.route
            }
            3 -> {
               SELECTEDINDEX = 3
                _route = Screen.Orders.route
            }
            4 -> {
                SELECTEDINDEX = 4
                _route = Screen.Wallet.route
            }
        }
    }
}

var SELECTEDINDEX = 0