package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id:String = "",
    val fullName: String = "",
    val username: String = "",
    val phoneNumber: String? = null,
    val email: String = "",
    val country: String = ""
)