package online.pascarl.coinx.authentication

import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import online.pascarl.coinx.R


suspend fun createAccount(email:String, password :String): Boolean? {

    return try {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .await()
        return true
    } catch (e: Exception) {
        false
    }
}


suspend fun signIn(email: String, password: String): String{

    return try {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .await()
        ""
    }catch (e: Exception) {
        "${e.printStackTrace()}"
    }


}

suspend fun signInWithGoogle(){
   val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId("241143851123-2psprgvd68oq4r2uh7lr9hqbj2cmrvpr.apps.googleusercontent.com")
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true)
                .build())
        .build()


}



