package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import online.pascarl.coinx.R
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.CryptoAdsScreenViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.ISBUYSELECTED
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.BuyOrSellState


@Composable
fun CryptoBuyOrSellToggle(
    cryptoAdsScreenViewModel:CryptoAdsScreenViewModel
) {
   /* cryptoAdsScreenViewModel.toggleBuyOrSell(
        state = if (ISBUYSELECTED == "Buy") BuyOrSellState.BUY else BuyOrSellState.SELL
    )*/

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Row(
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(20))
                        .background(
                            when ("Buy") {
                                "Buy" -> colorResource(id = R.color.grass_green)
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            cryptoAdsScreenViewModel.toggleBuyOrSell(state = BuyOrSellState.BUY)
                        }

                ) {
                    Text(
                        text = "Buy",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = when ("Buy") {
                            "Buy" -> Color.White
                            else -> Color.White
                        },
                        modifier = Modifier
                            .padding(10.dp)

                    )

                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(20))
                        .background(
                            when ("Sell") {
                                "Sell" -> Color.Red.copy(alpha = 0.8f)
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            cryptoAdsScreenViewModel.toggleBuyOrSell(state = BuyOrSellState.SELL)
                        }

                ) {
                    Text(
                        text = "Sell",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = when ("Sell") {
                            "Sell" -> Color.White
                            else -> Color.White
                        },
                        modifier = Modifier
                            .padding(10.dp)

                    )

                }
            }
            Spacer(modifier = Modifier.width(2.dp))
            CryptoAdsFilterCrypto(cryptoAdsScreenViewModel = cryptoAdsScreenViewModel)

        }
    }

}