package online.pascarl.coinx.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.bottom_bar_navigation.DashboardViewModel


@Preview(showSystemUi = true)
@Composable
fun Preview(){
    NoInternet()
}

@Composable
fun NoInternet(dashboardViewModel: DashboardViewModel = viewModel()){
    val context = LocalContext.current
    val isNetworkAvailable = isInternetAvailable(context = context)
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(UserDatabase.getInstance(LocalContext.current.applicationContext).userDao())
    )
    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.internet))
    var isPlaying by remember {
        mutableStateOf(true)
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )
    LaunchedEffect(key1 = progress){

        if (progress == 0f){
           isPlaying = true
        }
        if ( progress == 1f){
            isPlaying = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.app_white))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Connection failed.Please check your network settings.",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W300,
                color = Color.Gray,
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        isPlaying = true
                    }
            ) {/*
                Row(
                    modifier = Modifier.clickable {
                        scope.launch {
                            if (isNetworkAvailable)
                                dashboardViewModel.roomUser = roomDB.getUser("12345678") ?: RoomUser()
                                dashboardViewModel.getCryptoPrices()
                                dashboardViewModel.cryptoPrices()
                                dashboardViewModel.getUserData()
                                dashboardViewModel.getUserPortfolio()

                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh button",
                        tint =  colorResource(id = R.color.background),
                    )
                    Text(
                        text = "Refresh",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.background),
                    )
                }*/
            }
        }

    }
}
