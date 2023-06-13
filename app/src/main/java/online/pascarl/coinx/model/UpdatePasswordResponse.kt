package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordResponse(
    val status:Boolean,
    val message:String
)
