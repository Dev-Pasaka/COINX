package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.BuyConfirmationScreenViewModel


@Composable
fun BuyConfirmationLowerSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel,
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel,
    actionConfirm:() -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 30.dp, topEnd = 30.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
            ) {
                Text(
                    text = "Buy",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = imageLoader(symbol = sharedViewModel.orderData.value.cryptoSymbol),
                contentDescription = "Crypto symbol",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(25.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Fiat Amount",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = sharedViewModel.formatCurrency(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Price",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = sharedViewModel.formatCurrency(amount = sharedViewModel.buyAdData?.cryptoPrice ?: 0.0),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Crypto Amount",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "${sharedViewModel.youWillGet} ${sharedViewModel.buyAdData?.cryptoSymbol}",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ){
            Column(
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = "You will pay",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = sharedViewModel.formatCurrency(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                        onClick = actionConfirm
                    )
            ) {
                if (buyConfirmationScreenViewModel.orderStatus.orderIsCreating){
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        strokeWidth = 2.dp,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .size(15.dp)
                    )
                }else{
                    Text(
                        text = "Confirm",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}