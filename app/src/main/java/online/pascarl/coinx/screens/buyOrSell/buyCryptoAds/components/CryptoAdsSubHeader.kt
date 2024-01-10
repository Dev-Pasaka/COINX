package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CryptoAdsSubHeader() {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)

    ) {
        Text(
            text = "Ads are sorted from lowest price to highest price",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )

    }
}