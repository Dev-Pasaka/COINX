package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class SignIn(
    val email: String,
    val password: String
)