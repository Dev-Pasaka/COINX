package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.model.Ads
import online.pascarl.coinx.navigation.Screen

/*@Preview(showSystemUi = true)
@Composable
fun BuyCryptosSectionPreview(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel = viewModel()) {
    BuyOrSellCryptos(navController = navController, buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
    //EnterAmountToBuyOrSell(buyOrSellCryptosViewModel)

}*/

@Composable
fun BuyOrSellCryptos(
    navController: NavHostController,
    buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel = viewModel(),
    sharedViewModel: BuyOrSellSharedViewModel
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }


    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .alpha(alphaAnim.value)
    ) {
        BuyOrSellCryptoHeader(navController = navController)
        BuyOrSellToggle(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        SubHeader()
        BuyOrSellCryptosBody(
            navController = navController,
            buyOrSellCryptosViewModel = buyOrSellCryptosViewModel,
            sharedViewModel = sharedViewModel
        )
    }


}

@Composable
fun BuyOrSellCryptoHeader(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(360.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(360.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ){
                    navController.popBackStack()
                }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(2.dp)
                    .size(20.dp)
            )
        }

    }
}

@Composable
fun BuyOrSellCryptosBody(
    navController: NavHostController,
    buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel,
    sharedViewModel: BuyOrSellSharedViewModel
) {
    LazyColumn {
        items(buyOrSellCryptosViewModel.finalListOfAds.size) {
            OrderAdsItem(
                navController = navController,
                buyOrSellCryptosViewModel = buyOrSellCryptosViewModel,
                sharedViewModel = sharedViewModel,
                cryptoAd = buyOrSellCryptosViewModel.finalListOfAds[it]
            )
        }
    }
}

@Composable
fun SubHeader() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Ads are sorted from lowest price to highest price",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
fun OrderAdsItem(
    navController: NavHostController,
    cryptoAd: Ads,
    buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel,
    sharedViewModel: BuyOrSellSharedViewModel
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
                                text = "${cryptoAd.Username[0]}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onTertiary,

                                )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = cryptoAd.Username,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${cryptoAd.totalOrders} orders",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${cryptoAd.ordersCompleted}% completed",
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
                    text = "Ksh ${cryptoAd.cryptoPrice}",
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
                            color = when (buyOrSellCryptosViewModel.isBuySelected) {
                                "Buy" -> colorResource(id = R.color.grass_green)
                                else -> Color.Red.copy(alpha = 0.8f)
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            when (buyOrSellCryptosViewModel.isBuySelected) {
                                "Buy" -> {
                                  sharedViewModel.orderData.value = sharedViewModel.orderData.value.copy(
                                        username = cryptoAd.Username,
                                        adsType = cryptoAd.adType,
                                        cryptoName = cryptoAd.cryptoName,
                                        minLimit = cryptoAd.minOrder,
                                        maxLimit = cryptoAd.maxOrder,
                                        cryptoPrice = cryptoAd.cryptoPrice,
                                        cryptoAmount = cryptoAd.cryptoAmount,
                                        cryptoSymbol = cryptoAd.cryptoSymbol,
                                        paymentMethods = cryptoAd.paymentMethods
                                    )
                                    navController.navigate(Screen.BuyAmountScreen.route)
                                }

                                "Sell" -> {
                                    sharedViewModel.orderData.value = sharedViewModel.orderData.value.copy(
                                        username = cryptoAd.Username,
                                        adsType = cryptoAd.adType,
                                        cryptoName = cryptoAd.cryptoName,
                                        minLimit = cryptoAd.minOrder,
                                        maxLimit = cryptoAd.maxOrder,
                                        cryptoPrice = cryptoAd.cryptoPrice,
                                        cryptoAmount = cryptoAd.cryptoAmount,
                                        cryptoSymbol = cryptoAd.cryptoSymbol,
                                        paymentMethods = cryptoAd.paymentMethods
                                    )
                                    navController.navigate(Screen.SellAmountScreen.route)
                                }
                            }

                            buyOrSellCryptosViewModel.openOrCloseEnterAmountScreen()

                        }

                ) {
                    Text(
                        text = buyOrSellCryptosViewModel.isBuySelected,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.app_white),
                        modifier = Modifier
                            .padding(10.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun BuyOrSellToggle(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
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
                            when (buyOrSellCryptosViewModel.isBuySelected) {
                                "Buy" -> colorResource(id = R.color.grass_green)
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            buyOrSellCryptosViewModel.toggle(
                                toggleOption = "Buy"
                            )
                        }

                ) {
                    Text(
                        text = "Buy",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = when (buyOrSellCryptosViewModel.isBuySelected) {
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
                            when (buyOrSellCryptosViewModel.isBuySelected) {
                                "Sell" -> Color.Red.copy(alpha = 0.8f)
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ){
                            buyOrSellCryptosViewModel.toggle(toggleOption = "Sell")
                        }

                ) {
                    Text(
                        text = "Sell",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = when (buyOrSellCryptosViewModel.isBuySelected) {
                            "Sell" -> Color.White
                            else -> Color.White
                        },
                        modifier = Modifier
                            .padding(10.dp)

                    )

                }
            }
            Spacer(modifier = Modifier.width(2.dp))
            SelectCrypto(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)

        }
    }

}

@Composable
fun SelectCrypto(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel) {
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
                    buyOrSellCryptosViewModel.openAndCloseDropdown()
                }

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)

            ) {
                Image(
                    painter = imageLoader(symbol = buyOrSellCryptosViewModel.selectedItem),
                    contentDescription = "Crypto symbol",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = buyOrSellCryptosViewModel.selectedItem,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        DropdownMenu(
            expanded = buyOrSellCryptosViewModel.isDropdownExpanded,
            onDismissRequest = { buyOrSellCryptosViewModel.openAndCloseDropdown() },
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth(0.9f)

        ) {
            buyOrSellCryptosViewModel.listOfCryptoNamesAndSymbols.forEach {
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
                        buyOrSellCryptosViewModel.selectedItem = it.symbol
                        buyOrSellCryptosViewModel.filterCryptoBySelection()
                        buyOrSellCryptosViewModel.openAndCloseDropdown()
                    }
                )/* {

                  *//*  Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = imageLoader(symbol = it.symbol),
                            contentDescription = "Crypto symbol",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))

                    }*//*
                }*/
            }
        }
    }
}
