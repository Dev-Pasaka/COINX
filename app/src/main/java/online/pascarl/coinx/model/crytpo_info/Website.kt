package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class Website(
    val website: List<String>
)