package online.pascarl.coinx

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import online.pascarl.coinx.navigation.NavGraph
import online.pascarl.coinx.ui.theme.ISDARKTHEME

import online.pascarl.coinx.ui.theme.Material3AppTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    var darkTheme by mutableStateOf(ISDARKTHEME)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(darkTheme)
        setContent {
            Material3AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val systemUiControllerAndroid13 = MaterialTheme.colorScheme.surface
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            systemUiControllerAndroid13
                        }else if (darkTheme) Color(0xFF1C1B1E)
                        else Color(0xFF425AA9)
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    navController = rememberNavController()
                    NavGraph(navController = navController)


                }
            }
        }

    }





}

@Composable
fun MainScreen(){
    Column() {

    }

}