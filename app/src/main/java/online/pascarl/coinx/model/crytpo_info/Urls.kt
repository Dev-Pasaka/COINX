package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    val announcement: List<String>,
    val chat: List<String>,
    val explorer: List<String>,
    val facebook: List<Website>,
    val message_board: List<String>,
    val reddit: List<String>,
    val source_code: List<String>,
    val technical_doc: List<String>,
    val twitter: List<String>,
    val website: List<String>
)