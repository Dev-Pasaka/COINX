package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.model.OrderCreatedModel

@Composable
fun BuyOrderCreationMiddleSection(
    navController: NavHostController,
) {
    Column{
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ){
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Coinx is holding the seller's crypto in the escrow account",
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )

                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ){
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Coinx 24/7 customer support",
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )

                }
            }


        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8))
                .background(color = MaterialTheme.colorScheme.errorContainer)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                Text(
                    text = "For security reasons, only use your own account to transfer funds to the" +
                            "seller. Payments made from accounts not matching your verified KYC name" +
                            " will not be accepted.",
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                )
            }
        }
    }

}