package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.NavigationDrawer

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Swap(
    navController: NavHostController,
    bottomBarViewModel: BottomBarViewModel = viewModel()
){
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
    }

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
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            Text(text = "Swap")
        }
    }
}