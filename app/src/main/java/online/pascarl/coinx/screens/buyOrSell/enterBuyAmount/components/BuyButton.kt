package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.BuyAmountViewModel


@Composable
fun BuyButton(
    navController: NavHostController,
    buyAmountViewModel: BuyAmountViewModel,
    actionBuyButton:() -> Unit,
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxWidth()
    ) {
        FloatingActionButton(
            containerColor = if(
                buyAmountViewModel.fiatAmount > (buyAmountViewModel.buyAdData?.minLimit?.times(buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0)?:0.0 )
                && buyAmountViewModel.fiatAmount < (buyAmountViewModel.buyAdData?.maxLimit?.times(buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0) ?: 0.0)
            ) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 18.dp,
                focusedElevation = 18.dp
            ),
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = actionBuyButton
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Buy Icon",
                tint = if(
                    buyAmountViewModel.fiatAmount > (buyAmountViewModel.buyAdData?.minLimit?.times(buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0)?:0.0 )
                    && buyAmountViewModel.fiatAmount < (buyAmountViewModel.buyAdData?.maxLimit?.times(buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0) ?: 0.0)
                )MaterialTheme.colorScheme.onPrimary
                else  MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}