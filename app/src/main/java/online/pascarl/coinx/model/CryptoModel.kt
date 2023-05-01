package online.pascarl.coinx.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CryptoModel(
    val name: String = "Tether",
    val symbol: String,
    val price: Double = 138.03,
    val percentageChangeIn24Hrs: Double = -0.01,
    val marketCap:Double,
    val imageIcon: Painter,
    val firstGradientColor: Color
)
