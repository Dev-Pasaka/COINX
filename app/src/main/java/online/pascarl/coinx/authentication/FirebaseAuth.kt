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

suspend fun updatePassword(newPassword: String): Boolean{
    val user = Firebase.auth.currentUser;
   return try {
        user?.updatePassword(newPassword)
        true
    }catch (e: Exception){
        false
    }
}





