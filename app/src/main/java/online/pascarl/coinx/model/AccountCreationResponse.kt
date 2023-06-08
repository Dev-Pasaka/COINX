package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreationResponse(
    val isRegistered:Boolean,
    val message:String
)
