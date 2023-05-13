package online.pascarl.coinx.model

@kotlinx.serialization.Serializable
data class User(
    val fullName: String,
    val username: String,
    val email: String,
    val password: String,
    val phone: String? = null,
    val country: String = "Kenya",
)
