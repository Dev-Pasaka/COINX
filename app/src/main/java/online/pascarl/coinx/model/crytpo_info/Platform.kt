package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class Platform(
    val coin: Coin,
    val name: String
)