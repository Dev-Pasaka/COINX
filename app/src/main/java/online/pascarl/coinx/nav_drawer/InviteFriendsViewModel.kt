package online.pascarl.coinx.nav_drawer

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class InviteFriendsViewModel: ViewModel() {

    fun shareApp(context: Context){
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, """
                Download the Coinx app at https://play.google.com/store/apps/details?id=online.pascarl.coinx and
                experience an intuitive and seamless way to buy sell and trade your
                favourite cryptocurrencies with little to no fees.
            """.trimIndent())

            // (Optional) Here you're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Introducing content previews")
            type = "text/plain"
        }, null)
        try {
            ContextCompat.startActivity(context, share, null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}