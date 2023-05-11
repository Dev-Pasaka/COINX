package online.pascarl.coinx.datasource

import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.runBlocking
import online.pascarl.coinx.*
import online.pascarl.coinx.R
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.Cryptocurrency
import online.pascarl.coinx.networkcalls.getCryptoPrices


@Composable
fun expressCheckOut(): List<CryptoModel>{
    var apiData by remember {
        mutableStateOf((listOf<Cryptocurrency>()))
    }
        runBlocking {
            apiData = getCryptoPrices()
        }

    val colorGradient = listOf(
        colorResource(id = R.color.orange),
        colorResource(id = R.color.purple_200),
        colorResource(id = R.color.grass_green),
        colorResource(id = R.color.bamboo)
    ).random()
    val newData:MutableList<CryptoModel> = mutableListOf()
        for (liveData in apiData){
            newData.add(
                CryptoModel(
                    name = liveData.name.replace("\"", ""),
                    symbol = liveData.symbol.replace("\"", ""),
                    price = liveData.price!!.toDouble(),
                    marketCap = liveData.marketCap!!.toDouble(),
                    percentageChangeIn24Hrs = liveData.percentageChange24h!!.toDouble(),
                    imageIcon = imageLoader(symbol = liveData.symbol.replace("\"", "")),
                    firstGradientColor = colorGradient
                )
            )
        }

    return  newData
}

object FetchCryptoPrices{
    var loadData = listOf<CryptoModel>()
}
