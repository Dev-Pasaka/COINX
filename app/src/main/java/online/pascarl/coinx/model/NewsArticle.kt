package online.pascarl.coinx.model


data class FormattedArticleList(
    val title: String,
    val imageUrl: String,
    val byAuthor: String,
    val publishedDate: String,
    val externalLink: String,
    val description: String,
)
data class Article(
    val title: String,
    val author: String,
    val published_date: String,
    val published_date_precision: String,
    val link: String,
    val clean_url: String,
    val excerpt: String,
    val summary: String,
    val rights: String,
    val rank: Int,
    val topic: String,
    val country: String,
    val language: String,
    val authors: String,
    val media: String,
    val is_opinion: Boolean,
    val twitter_account: String,
    val _score: Double,
    val _id: String
)
