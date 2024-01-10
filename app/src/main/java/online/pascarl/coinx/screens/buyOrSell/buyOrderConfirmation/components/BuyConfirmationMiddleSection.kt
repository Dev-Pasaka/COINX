package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material.icons.outlined.SwapHorizontalCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.BuyConfirmationScreenViewModel

@Composable
fun BuyConfirmationMiddleSection(
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel,
    sharedViewModel: BuyCryptoSharedViewModel,
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ){
                Text(
                    text = "P2P Trading ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.SwapHorizontalCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sharedViewModel.orderData.value.username,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You bought ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${sharedViewModel.youWillGet} ${sharedViewModel.buyAdData?.cryptoSymbol}",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .clickable {

                }
        ) {
            Text(
                text = "How to buy ",

                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
            Icon(
                imageVector = Icons.Outlined.PlayCircleFilled,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

    }
}