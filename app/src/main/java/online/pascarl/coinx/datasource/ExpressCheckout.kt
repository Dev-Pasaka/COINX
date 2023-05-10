package online.pascarl.coinx.datasource

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.runBlocking
import online.pascarl.coinx.*
import online.pascarl.coinx.R
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.Cryptocurrency
import java.util.*


@Composable
fun expressCheckOut(): List<CryptoModel>{
    var apiData by remember {
        mutableStateOf((listOf<Cryptocurrency>()))
    }
    val timer = Timer()
  //  timer.scheduleAtFixedRate(timerTask {
        runBlocking {
            apiData = request()
        }
//    }, 0L, 30_000L)
/*
    val data1 = mutableListOf(
        CryptoModel(
            name ="Bitcoin", symbol = "BTC", price = 4544849.094, percentageChangeIn24Hrs = -2.32,
            marketCap = 89475.430,
            imageIcon = imageLoader(symbol = "Btc"), firstGradientColor = colorResource(id = R.color.orange)
        ),
        CryptoModel(
            name ="Ethereum", symbol = "ETH", price = 394849.094, percentageChangeIn24Hrs = 3.32,
            marketCap = 438959475.430,
            imageIcon = imageLoader(symbol = "ETH"), firstGradientColor =
            colorResource(id = R.color.blue)
        ),
        CryptoModel(
            name ="Tether", symbol = "USDT", price = 137.09, percentageChangeIn24Hrs = -0.30,
            marketCap = 95089475.430,
            imageIcon = imageLoader(symbol = "USDT"), firstGradientColor =
            colorResource(id = R.color.grass_green)
        ),
        CryptoModel(
            name ="Binance", symbol = "BUSD", price = 137.03, percentageChangeIn24Hrs = 0.32,
            marketCap = 489475.430,
            imageIcon = imageLoader(symbol = "BUSD"), firstGradientColor =
            colorResource(id = R.color.bamboo)
        )
    )*/
//    val express by remember {
//        mutableStateOf(data)
//    }
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