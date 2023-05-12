package online.pascarl.coinx

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.*
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import java.text.NumberFormat
import java.util.*

fun formatCurrency(symbol:String, value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance()
    val currency = Currency.getInstance(symbol)
    formatter.currency = currency
    formatter.maximumFractionDigits = currency.defaultFractionDigits
    val formatted = formatter.format(value)
    return formatted.replace(currency.symbol, "${currency.symbol} ")
}

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
    
    return rememberImagePainter(data = imageUri)
    
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