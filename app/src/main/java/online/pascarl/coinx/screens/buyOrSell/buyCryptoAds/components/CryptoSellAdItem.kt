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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetSellAds


@Composable
fun CryptoSellAdsItem(
    sellAd: GetSellAds,
    navController: NavHostController,
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.tertiary,
                                )
                        ) {
                            Text(
                                text = sellAd.merchantUsername.first().uppercase(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onTertiary,

                                )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = sellAd.merchantUsername,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Total orders ${sellAd.ordersMade}",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Completed ${sellAd.ordersCompletedByPercentage.toInt()}%",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }

            }

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Price",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "Ksh ${sellAd.cryptoPrice.toInt()}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(20))
                        .background(
                            color = Color.Red.copy(alpha = 0.8f)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            buyCryptoSharedViewModel.sellAdData = sellAd
                            navController.navigate(route = Screen.SellAmountScreen.route)
                        }
                ){

                    Text(
                        text = "Sell",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 8.dp)

                    )

                }
            }
        }
    }
}