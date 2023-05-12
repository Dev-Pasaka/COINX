package online.pascarl.coinx.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import online.pascarl.coinx.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.datasource.expressCheckOut
import online.pascarl.coinx.navigation.Screen



@Composable
fun AnimatedSplashScreen(navController: NavHostController){


    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )


    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(Screen.Register.route)
    }
    SplashScreen(alpha = alphaAnim.value)
}

@SuppressLint("ResourceAsColor")
@Composable
fun SplashScreen(image: Painter= painterResource(id = R.drawable.coinx), alpha:Float){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter =image ,
                contentDescription = "APP LOGO",
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}