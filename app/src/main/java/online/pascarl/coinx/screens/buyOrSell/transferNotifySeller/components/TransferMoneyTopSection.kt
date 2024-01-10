package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import online.pascarl.coinx.getCryptoLogoUrl
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel

@Composable
fun TransferMoneyTopSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel
) {
    val context = LocalContext.current
    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                        navController.popBackStack()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)

                )


            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = getCryptoLogoUrl(symbol = sharedViewModel.buyAdData?.cryptoSymbol ?: "" )),
                contentDescription = "Crypto symbol",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
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
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pay the seller",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 2.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "Order will be cancelled in ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
                Text(
                    text = "13.15",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall,

                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = sharedViewModel.formatCurrency(amount = sharedViewModel.youWillPay ?: 0.0),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick = {
                    sharedViewModel.copyToClipboard(
                        context = context,
                        text = sharedViewModel.youWillPay.toString(),
                        message = "Amount copied"
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(20.dp)
                            .clip(RoundedCornerShape(360.dp))
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
            ) {

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