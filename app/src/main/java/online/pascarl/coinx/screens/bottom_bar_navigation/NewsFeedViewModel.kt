package online.pascarl.coinx.screens.bottom_bar_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.R
import online.pascarl.coinx.model.Article
import online.pascarl.coinx.model.FormattedArticleList

class NewsFeedViewModel(): ViewModel() {
    var searchInput by mutableStateOf("")
    var isLoading:Boolean by  mutableStateOf(true)

    var formattedArticleList = mutableStateListOf<FormattedArticleList>()
        private set

    var showWebView by mutableStateOf(false)
        private set
    var webViewUrl by mutableStateOf("")
        private set

    fun openWebView(url:String){
        webViewUrl = url
        showWebView = true
    }
    fun closeWebView(){
        showWebView = false
        isLoading = false
    }

    fun onLoadingFinished(){
        isLoading = false
    }

    suspend fun getNewsArticle(): String? {
        var articleList: List<*>
        val finalArticleList:MutableList<FormattedArticleList> = mutableListOf()
        val client = KtorClient.httpClient
        val response = try {
            client.get<String> {
                url("https://gnews.io/api/v4/search?q=cryptocurrencies&apikey=09b236d5886b2bcd1a681fca48d22fec&max=100&lang=en")
            }.toString()
        }catch (_:Exception){
            null
        }
        val jsonResponseString: String = response?: ""
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
        formattedArticleList.clear()
        formattedArticleList.addAll(finalArticleList)
        return finalArticleList.toString()
    }
}