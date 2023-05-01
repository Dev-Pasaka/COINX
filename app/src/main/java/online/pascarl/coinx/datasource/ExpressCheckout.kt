package online.pascarl.coinx.datasource

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import online.pascarl.coinx.R
import online.pascarl.coinx.model.CryptoModel

@Composable
fun expressCheckOut(): List<CryptoModel>{
    return  listOf<CryptoModel>(
        CryptoModel(
            name ="Bitcoin", symbol = "BTC", price = 4544849.094, percentageChangeIn24Hrs = -2.32,
            marketCap = 89475.430,
            imageIcon = painterResource(id = R.drawable.bitcoin_icon), firstGradientColor =
            colorResource(id = R.color.orange)
        ),
        CryptoModel(
            name ="Ethereum", symbol = "ETH", price = 394849.094, percentageChangeIn24Hrs = 3.32,
            marketCap = 438959475.430,
            imageIcon = painterResource(id = R.drawable.ethereum_icon), firstGradientColor =
            colorResource(id = R.color.blue)
        ),
        CryptoModel(
            name ="Tether", symbol = "USDT", price = 137.09, percentageChangeIn24Hrs = -0.30,
            marketCap = 95089475.430,
            imageIcon = painterResource(id = R.drawable.tether_icon), firstGradientColor =
            colorResource(id = R.color.grass_green)
        ),
        CryptoModel(
            name ="Binance", symbol = "BUSD", price = 137.03, percentageChangeIn24Hrs = 0.32,
            marketCap = 489475.430,
            imageIcon = painterResource(id = R.drawable.busd_icon), firstGradientColor =
            colorResource(id = R.color.bamboo)
        )
    )
}