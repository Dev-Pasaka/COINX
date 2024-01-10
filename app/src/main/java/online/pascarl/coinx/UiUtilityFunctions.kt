package online.pascarl.coinx

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.ViewTreeObserver
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import online.pascarl.coinx.model.CryptoSymbols
import java.util.*



fun getCurrentTime(): String{
    val currentTime = Calendar.getInstance().time.hours
    val salutation = if(currentTime in 0..12){
        "Good morning"
    }
    else if (currentTime in 13..15){
        "Good afternoon"

    }
    else{
        "Good evening"

    }
    return salutation
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun imageLoader(
    symbol: String = "eth",
    imageUri:String = "https://coinicons-api.vercel.app/api/icon/${symbol.lowercase()}"
): ImagePainter{
    
    val painter = rememberImagePainter(data = imageUri)
    return painter
    
}


@Composable
 fun isInternetAvailable(context: Context): Boolean {
    var result by remember {
        mutableStateOf(false)
    }
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}


@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideInAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { -40 }
        ) + expandHorizontally(
            expandFrom = Alignment.End
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut(),
        content = content,
        initiallyVisible = false
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomUpAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { 100 }
        ) + expandVertically(
            expandFrom = Alignment.Bottom
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        content = content,
        initiallyVisible = false
    )
}
fun getCryptoLogoUrl(input: String = CryptoSymbols.input, symbol: String = "eth"):String?{
    val map = mutableMapOf<String, String>()
    // Remove the curly braces at the beginning and end of the input string
    val trimmedInput = input.trimStart('{').trimEnd('}')
    // Split the input into individual key-value pairs using ', ' as a delimiter
    val keyValuePairs = trimmedInput.split(", ")
    // Iterate through each key-value pair and extract the key and value
    keyValuePairs.forEach {
        val (key, value) = it.split("=")
        val trimmedKey = key.trim()
        val trimmedValue = value.trim()
        map[trimmedKey] = trimmedValue
    }
    // Print the entire map
    println("Resulting Map: ${map[symbol.uppercase()]}")
    return map[symbol.uppercase()]
}