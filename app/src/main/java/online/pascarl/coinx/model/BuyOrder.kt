package online.pascarl.coinx.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue

data class BuyOrder(
    val adId:String = "",
    val fiatAmount:Double = 0.0,
    val cryptoAmount:Double = 0.0,
    val cryptoName:String = "",
    val cryptoSymbol:String = ""
)
