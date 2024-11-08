package online.pascarl.coinx.screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import online.pascarl.coinx.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.delay
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository


@Composable
fun AnimatedSplashScreen(navController: NavHostController, splashScreenViewModel: SplashScreenViewModel = viewModel()){
    val context = LocalContext.current
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(UserDatabase.getInstance(LocalContext.current.applicationContext).userDao())
    )


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
        delay(3000)
        navController.popBackStack()

        val roomResult = try {
            roomDB.getUser("12345678")
        }catch (e:Exception){
            null
        }
        roomResult?.let {
            splashScreenViewModel.roomUser = roomResult
        }
        splashScreenViewModel.getUserData()
        if (splashScreenViewModel.isUserSignedIn){
            navController.navigate(Screen.Dashboard.route)
        }
        else navController.navigate(Screen.Register.route)
    }

    SplashScreen(alpha = alphaAnim.value)
}

@SuppressLint("ResourceAsColor")
@Composable
fun SplashScreen(image: Painter= painterResource(id = R.drawable.coinx), alpha:Float){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Coinx",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}