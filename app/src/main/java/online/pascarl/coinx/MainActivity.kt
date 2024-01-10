package online.pascarl.coinx

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import online.pascarl.coinx.navigation.NavGraph
import online.pascarl.coinx.ui.theme.ISDARKTHEME

import online.pascarl.coinx.ui.theme.Material3AppTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var appUpdateManager:AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        checkForAppUpdates()
        setContent {
            Material3AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                // Remember a SystemUiController
                val isDarkMode = isSystemInDarkTheme()
                val systemUiController = rememberSystemUiController()
                val systemUiControllerAndroid13 = MaterialTheme.colorScheme.background
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            systemUiControllerAndroid13
                        } else if (isDarkMode) Color.Transparent
                        else Color(0xFFE8DEF8)
                    )
                    systemUiController.setStatusBarColor(
                        color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            systemUiControllerAndroid13
                        } else if (isDarkMode) Color.Transparent
                        else Color(0xFFFFFBFF)
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

    private fun checkForAppUpdates(){
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when(updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable && isUpdateAllowed){
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType,
                    this,
                    123
                )
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != RESULT_OK){
            println("Something went wrong updating")
        }
    }


}

@Composable
fun MainScreen() {
    Column() {

    }

}