package online.pascarl.coinx.model

@kotlinx.serialization.Serializable
data class Cryptocurrency(
    val id: Int,
    var name: String,
    var symbol: String,
    var price: String?,
    val volume24h: String?,
    val volumeChange24h: String?,
    val percentageChange1h: String?,
    var percentageChange24h: String?,
    val percentageChange7d: String?,
    val percentageChange30d: String?,
    val percentageChange60d: String?,
    val percentageChange90d: String?,
    var marketCap: String?,
    val fullyDilutedMarketCap: String?,
    var logoUrl:String? = ""
)

