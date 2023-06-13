package online.pascarl.coinx.model

@kotlinx.serialization.Serializable
data class UserRegistrationResponse(
    val message:String,
    val isRegistered: Boolean
)
