package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class Status(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int,
    val error_message: String?,
    val notice: String?,
    val timestamp: String
)