package online.pascarl.coinx.nav_drawer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.R
import java.util.Locale

class ContactUsViewModel: ViewModel() {

    var isDarkMode by mutableStateOf(false)

    val facebookLightIcon = R.drawable.facebook_light_icon
    val facebookDarkIcon = R.drawable.facebook_dark_icon
    val twitterLightIcon = R.drawable.twitter_light_icon
    val twitterDarkIcon = R.drawable.twitter_dark_icon
    val instagramLightIcon = R.drawable.instagram_light_icon
    val instagramDarkIcon = R.drawable.instagram_dark_icon
    val websiteDarkIcon = R.drawable.website_dark_icon
    val websiteLightIcon = R.drawable.website_light_icon

    fun launchPhoneDial(context: Context){
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
        }
        dialIntent.data = Uri.parse("tel:"+"+254717722324")
        try {
            ContextCompat.startActivity(context, dialIntent, null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun launchEmail(context: Context){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@coinx.co.ke"))
            putExtra(Intent.EXTRA_SUBJECT, "Customer Inquiry")
        }
        try {
            ContextCompat.startActivity(context, intent, null)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun launchMap(context: Context){
        // Create a Uri from an intent string. Use the result to create an Intent.
        val gmmIntentUri = Uri.parse("geo:-1.26440,36.80712")

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
       // Make the Intent explicit by setting the Google Maps package
        //mapIntent.setPackage("com.google.android.apps.maps")
        try {
            ContextCompat.startActivity(context, mapIntent, null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    }