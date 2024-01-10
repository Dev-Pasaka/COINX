package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
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

@Composable
fun TransferMoneyMiddleSection(sharedViewModel: BuyCryptoSharedViewModel) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),

            ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                ) {
                    Text(
                        text = "Leave the coinx app, open your selected banking or payment platform with an" +
                                "account name that matches your verified name on coinx, and transfer the" +
                                "funds to the seller's account provided below.",
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        PaymentMethods(buyCryptoSharedViewModel = sharedViewModel)
                    }
                }
            }

        }

    }

}
