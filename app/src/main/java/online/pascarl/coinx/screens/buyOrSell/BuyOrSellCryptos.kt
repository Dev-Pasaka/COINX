package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
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


    Column(modifier = Modifier.alpha(alphaAnim.value)) {
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
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Go Back",
                tint = Color.DarkGray,
                modifier = Modifier
                    .padding(2.dp)
                    .size(15.dp)
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
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 12.sp,
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
                                    color = colorResource(id = R.color.background)
                                )
                        ) {
                            Text(
                                text = "${cryptoAd.Username[0]}",
                                style = MaterialTheme.typography.body2,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                color = Color.White,
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = cryptoAd.Username,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.background)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${cryptoAd.totalOrders} orders",
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontSize = 12.sp,
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${cryptoAd.ordersCompleted}% completed",
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = colorResource(id = R.color.grass_green),
                            fontSize = 12.sp,
                        )
                    }

                }

            }

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "Ksh ${cryptoAd.cryptoPrice}",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(20))
                        .background(
                            color = when (buyOrSellCryptosViewModel.isBuySelected) {
                                "Buy" -> colorResource(id = R.color.grass_green)
                                else -> Color.Red
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
                                        paymentMethod = cryptoAd.paymentMethod
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
                                        paymentMethod = cryptoAd.paymentMethod
                                    )
                                    navController.navigate(Screen.SellAmountScreen.route)
                                }
                            }

                            buyOrSellCryptosViewModel.openOrCloseEnterAmountScreen()

                        }

                ) {
                    Text(
                        text = buyOrSellCryptosViewModel.isBuySelected,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
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
                                else -> Color.LightGray
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
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
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
                                "Sell" -> Color.Red
                                else -> Color.LightGray
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
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
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
                .background(color = colorResource(id = R.color.light_gray))
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
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
            }
        }

        DropdownMenu(
            expanded = buyOrSellCryptosViewModel.isDropdownExpanded,
            onDismissRequest = { buyOrSellCryptosViewModel.openAndCloseDropdown() },
            modifier = Modifier
                .fillMaxWidth(0.9f)

        ) {
            buyOrSellCryptosViewModel.listOfCryptoNamesAndSymbols.forEach {
                DropdownMenuItem(
                    onClick = {
                        buyOrSellCryptosViewModel.selectedItem = it.symbol
                        buyOrSellCryptosViewModel.filterCryptoBySelection()
                        buyOrSellCryptosViewModel.openAndCloseDropdown()
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = imageLoader(symbol = it.symbol),
                            contentDescription = "Crypto symbol",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = it.name +
                                    " ${it.symbol}",
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}
