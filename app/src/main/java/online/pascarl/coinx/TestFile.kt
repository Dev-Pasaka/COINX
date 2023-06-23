package online.pascarl.coinx

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.FormattedArticleList


suspend fun main(){
    println(getNewsArticle())
}

suspend fun getNewsArticle(): String? {
    var articleList: List<*>
    val finalArticleList:MutableList<FormattedArticleList> = mutableListOf()
    val client = KtorClient.httpClient
    val response = client.get<String> {
        url("https://gnews.io/api/v4/search?q=cryptocurrencies&apikey=09b236d5886b2bcd1a681fca48d22fec&max=100&lang=en")
    }.toString()
    val jsonResponseString: String = response
    val jsonResponseObj = Json.parseToJsonElement(jsonResponseString) as JsonObject
    articleList = jsonResponseObj["articles"] as List<*>
    for (article in articleList){
        val articleObj= article as Map<*, *>
        val published_date = articleObj["publishedAt"]
        val link = articleObj["url"]
        val title = articleObj["title"]
        val summary = articleObj["description"]
        val media = articleObj["image"]

        val authorObj = articleObj["source"] as Map<*,*>
        val author = authorObj["name"]

        finalArticleList.add(
            FormattedArticleList(
                title = title.toString().replace("\"", ""),
                externalLink = link.toString().replace("\"", ""),
                description = summary.toString().replace("\"", ""),
                publishedDate = published_date.toString().replace("\"", ""),
                byAuthor = author.toString().replace("\"", ""),
                imageUrl = media.toString().replace("\"", "")

            )
        )

    }
    return finalArticleList.toString()
}
