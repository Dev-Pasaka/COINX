package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.BuyAmountViewModel

@Composable
fun BuyNumberKey(
    buyAmountViewModel: BuyAmountViewModel,
    numberKey: String = "1",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
            ) {
                buyAmountViewModel.cryptoBuyAmount += numberKey
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = numberKey,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}