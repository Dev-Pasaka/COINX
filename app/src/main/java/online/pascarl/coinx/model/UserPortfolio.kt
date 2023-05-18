package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPortfolio(
    val balance: Double = 0.0,
    val assets: List<CryptoPrice> = listOf()
)
