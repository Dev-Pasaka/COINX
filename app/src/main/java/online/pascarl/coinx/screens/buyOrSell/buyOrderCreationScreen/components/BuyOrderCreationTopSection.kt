package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components

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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import online.pascarl.coinx.getCryptoLogoUrl
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.model.OrderCreatedModel
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel

@Composable
fun BuyOrderCreationTopSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel
) {
    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
     /*       Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
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
                        .clip(RoundedCornerShape(360.dp))

                )

            }*/
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

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
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Order Created",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Pay seller within ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "15 mins",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}