package online.pascarl.coinx.authentication

import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import online.pascarl.coinx.R
import online.pascarl.coinx.model.User
import online.pascarl.coinx.networkcalls.registerUser
import online.pascarl.coinx.networkcalls.validateUserCreationResponse


suspend fun createAccount(username:String, fullName:String, email:String, password :String): String? {
    val auth = Firebase.auth

    return try {
        auth.createUserWithEmailAndPassword(email, password)
                .await()
        val registerUser = registerUser(
            user = User(
                username = username,
                fullName = fullName,
                email = email,
                password = password
            )
        )
        if (validateUserCreationResponse(response = registerUser) == true) return  "user created" else return  null


    }
    catch (_: FirebaseAuthUserCollisionException){
        "user exists"
    }
    catch (_: Exception) {
            null
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





