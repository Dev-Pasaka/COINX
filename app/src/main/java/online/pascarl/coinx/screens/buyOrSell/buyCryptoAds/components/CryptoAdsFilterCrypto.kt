package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.CryptoAdsScreenViewModel


@Composable
fun CryptoAdsFilterCrypto(cryptoAdsScreenViewModel: CryptoAdsScreenViewModel) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(90.dp)
                .clip(RoundedCornerShape(20))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ){
                    cryptoAdsScreenViewModel.openAndCloseDropdown()
                }

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)

            ) {
                Image(
                    painter = imageLoader(symbol = cryptoAdsScreenViewModel.selectedItem),
                    contentDescription = "Crypto symbol",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = cryptoAdsScreenViewModel.selectedItem,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        DropdownMenu(
            expanded = cryptoAdsScreenViewModel.isDropdownExpanded,
            onDismissRequest = { cryptoAdsScreenViewModel.openAndCloseDropdown() },
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth(0.9f)


        ) {
            cryptoAdsScreenViewModel.listOfCryptoNamesAndSymbols.forEach {
                DropdownMenuItem(
                    leadingIcon = { Image(
                        painter = imageLoader(symbol = it.symbol),
                        contentDescription = "Crypto symbol",
                        modifier = Modifier.size(30.dp)
                    )},
                    text = {
                        Text(
                            text = it.name +
                                    " ${it.symbol}",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    },
                    onClick = {
                        cryptoAdsScreenViewModel.selectedItem = it.symbol
                        cryptoAdsScreenViewModel.filterCryptoBySelection()
                        cryptoAdsScreenViewModel.openAndCloseDropdown()
                    }
                )
            }
        }
    }
}