package online.pascarl.coinx

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.datasource.expressCheckOut
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.navigation.NavGraph
import online.pascarl.coinx.screens.AnimatedSplashScreen
import online.pascarl.coinx.ui.theme.COINXTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private var keepSplashScreenOpened = true
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COINXTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ){
                    navController = rememberNavController()
                    AnimatedSplashScreen(navController =navController )
                    NavGraph(navController = navController)

                }



            }
        }

    }



}

object FetchCryptoPrices{
    var loadData = listOf<CryptoModel>()
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    COINXTheme {
        Greeting("Android")
    }
}