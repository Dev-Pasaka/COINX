package online.pascarl.coinx.model.crytpo_info

import kotlinx.serialization.Serializable

@Serializable
data class ETH(
    val category: String,
    val contract_address: List<ContractAddres>,
    val date_added: String,
    val date_launched:String?,
    val description: String,
    val id: Int,
    val infinite_supply: Boolean,
    val is_hidden: Int,
    val logo: String,
    val name: String,
    val notice: String,
    val platform: Platform,
    val self_reported_circulating_supply:Double?,
    val self_reported_market_cap:Double?,
    val self_reported_tags:Double?,
    val slug: String,
    val subreddit: String,
    val symbol: String,
    val tag_groups: List<String>,
    val tag_names: List<String>,
    val tags: List<String>,
    val twitter_username: String,
    val urls: Urls
)