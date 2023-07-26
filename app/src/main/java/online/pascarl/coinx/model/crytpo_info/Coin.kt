package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    val id: String,
    val name: String,
    val slug: String,
    val symbol: String
)