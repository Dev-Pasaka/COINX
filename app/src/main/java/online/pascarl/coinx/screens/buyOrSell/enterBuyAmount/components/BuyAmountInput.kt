package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.BuyAmountViewModel


@Composable
fun BuyAmountInput(buyAmountViewModel: BuyAmountViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = buyAmountViewModel.formatCurrency(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "1 ${buyAmountViewModel.buyAdData?.cryptoSymbol} = ${buyAmountViewModel.buyAdData?.cryptoPrice?.toInt()} KES",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "${String.format("%.6f", buyAmountViewModel.fiatAmount/ (buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0))} ${buyAmountViewModel.buyAdData?.cryptoSymbol}",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )


    }
}
