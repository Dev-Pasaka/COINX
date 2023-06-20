package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.NavigationDrawer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Swap(
    navController: NavHostController,
    bottomBarViewModel: BottomBarViewModel = viewModel()
){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { NavigationDrawer(navController = navController) },
        bottomBar = { CustomBottomNavigation(
            navController = navController,
            bottomBarViewModel = bottomBarViewModel
        )
        }
    ) {
        Column(

        ) {
            Text(text = "Swap")
        }
    }
}