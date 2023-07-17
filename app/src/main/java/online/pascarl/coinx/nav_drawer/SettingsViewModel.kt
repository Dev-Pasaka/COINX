package online.pascarl.coinx.nav_drawer

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.R
import online.pascarl.coinx.ui.theme.ISDARKTHEME

class SettingsViewModel:ViewModel() {
    var checked by  mutableStateOf(true)
    var isDarkTheme by mutableStateOf(false)

    var mode by mutableStateOf("Systems default")
        private set

    // Icon isn't focusable, no need for content description
    val icon: (@Composable () -> Unit)? = if (checked) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }

    fun changeTheme(){
        ISDARKTHEME = !ISDARKTHEME
    }


}