package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class CryptoInfo(
    val data: Data,
    val status: Status
)