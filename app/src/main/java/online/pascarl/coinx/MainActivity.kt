package online.pascarl.coinx

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.datasource.FetchCryptoPrices
import online.pascarl.coinx.datasource.expressCheckOut
import online.pascarl.coinx.navigation.NavGraph
import online.pascarl.coinx.ui.theme.COINXTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            COINXTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ){
                    FetchCryptoPrices.loadData = expressCheckOut()
                    navController = rememberNavController()

                    NavGraph(navController = navController)
                }


            }
        }

    }



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

fun hideSystemUI() {

    //Hides the ugly action bar at the top


    //Hide the status bars


}