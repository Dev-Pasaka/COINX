package online.pascarl.coinx.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CryptoModel(
    var name: String = "Tether",
    var symbol: String = "",
    var price: Double = 138.03,
    var percentageChangeIn24Hrs: Double = -0.01,
    var marketCap:Double = 0.0,
    var logoUrl:String? = ""
)
