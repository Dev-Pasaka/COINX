package online.pascarl.coinx.model

@kotlinx.serialization.Serializable
data class Response(
    val message:String,
    val isRegistered: Boolean
)
