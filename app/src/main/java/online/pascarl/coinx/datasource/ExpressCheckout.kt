package online.pascarl.coinx.datasource

import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.runBlocking
import online.pascarl.coinx.*
import online.pascarl.coinx.R
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.Cryptocurrency
import online.pascarl.coinx.networkcalls.getCryptoPrices



fun expressCheckOut(): List<CryptoModel>{
    var apiData  = listOf<Cryptocurrency>()

        runBlocking {
            apiData = getCryptoPrices()
        }

    val newData:MutableList<CryptoModel> = mutableListOf()
        for (liveData in apiData){
            newData.add(
                CryptoModel(
                    name = liveData.name.replace("\"", ""),
                    symbol = liveData.symbol.replace("\"", ""),
                    price = liveData.price!!.toDouble(),
                    marketCap = liveData.marketCap!!.toDouble(),
                    percentageChangeIn24Hrs = liveData.percentageChange24h!!.toDouble(),
                )
            )
        }

    return  newData
}


@Composable
fun getCryptoPrices(): List<CryptoModel>?{

    FetchCryptoPrices.loadData = expressCheckOut()
    return FetchCryptoPrices.loadData.ifEmpty {
        null
    }

}