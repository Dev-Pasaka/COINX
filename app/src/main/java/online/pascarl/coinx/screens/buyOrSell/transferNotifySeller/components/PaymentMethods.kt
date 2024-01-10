package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel

@Composable
fun PaymentMethods(buyCryptoSharedViewModel: BuyCryptoSharedViewModel){
    val context = LocalContext.current
    if (buyCryptoSharedViewModel.buyAdData?.paymentMethod?.mpesaSafaricom != null){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "M-Pesa Safaricom" ,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Phone Number ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
                SelectionContainer {
                    Text(
                        text = buyCryptoSharedViewModel.buyAdData?.paymentMethod?.mpesaSafaricom!!.phone ,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(2.dp)
                        .size(15.dp)
                        .clickable {
                            buyCryptoSharedViewModel.copyToClipboard(
                                context = context,
                                text = buyCryptoSharedViewModel.buyAdData?.paymentMethod?.mpesaSafaricom!!.phone
                            )
                        }

                )
            }

        }

    }

}