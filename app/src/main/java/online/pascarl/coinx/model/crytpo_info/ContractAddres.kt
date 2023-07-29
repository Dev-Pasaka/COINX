package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class ContractAddres(
    val contract_address: String,
    val platform: Platform
)