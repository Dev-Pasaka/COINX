package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Backspace
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen


@Composable
fun BuyAmountScreen(
    navController: NavHostController,
    buyAmountViewModel: BuyAmountViewModel = viewModel(),
    sharedViewModel: BuyOrSellSharedViewModel
) {
    val scrollState = rememberScrollState()
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
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .alpha(alphaAnim.value)
            .verticalScroll(state = scrollState)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(360.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                            ) {
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = sharedViewModel.orderData.value.adsType,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = imageLoader(symbol = sharedViewModel.orderData.value.cryptoSymbol),
                        contentDescription = "Crypto Icon",
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(360.dp))

                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Limit ${sharedViewModel.orderData.value.minLimit} - ${sharedViewModel.orderData.value.maxLimit}" ,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.background),
                        fontSize = 12.sp,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Payment Method",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = sharedViewModel.orderData.value.paymentMethod,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.background),
                    fontSize = 12.sp,
                )
            }
        }
        BuyAmountInput(buyAmountViewModel = buyAmountViewModel, sharedViewModel = sharedViewModel)
        BuyNumberPad(
            navController = navController,
            sharedViewModel = sharedViewModel,
            buyAmountViewModel = buyAmountViewModel
        )
        BuyButton(
            navController = navController,
            buyAmountViewModel = buyAmountViewModel,
            sharedViewModel = sharedViewModel
        )

    }
}

@Composable
fun BuyNumberPad(
    navController:NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel,
    buyAmountViewModel: BuyAmountViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "1", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "2", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "3", buyAmountViewModel = buyAmountViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "4", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "5", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "6", buyAmountViewModel = buyAmountViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "7", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "8", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "9", buyAmountViewModel = buyAmountViewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = ".", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "0", buyAmountViewModel = buyAmountViewModel)
            Column(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        buyAmountViewModel.cryptoBuyAmount =
                            buyAmountViewModel.removeLastCharacter(
                                input = buyAmountViewModel.cryptoBuyAmount
                            )
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Backspace,
                    contentDescription = "Back Space",
                    tint = Color.Gray,
                )
            }

        }

    }
}

@Composable
fun BuyNumberKey(
    buyAmountViewModel: BuyAmountViewModel,
    numberKey: String = "1",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
            ) {
                buyAmountViewModel.cryptoBuyAmount += numberKey
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = numberKey,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 24.sp,
            )
        }
    }
}

@Composable
fun BuyAmountInput(buyAmountViewModel: BuyAmountViewModel, sharedViewModel: BuyOrSellSharedViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = buyAmountViewModel.formatCurrency(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            fontSize = 24.sp,
        )
        Text(
            text = "1 ${sharedViewModel.orderData.value.cryptoSymbol} = ${sharedViewModel.orderData.value.cryptoPrice} KES",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp,
        )
        Text(
            text = "${String.format("%.6f", buyAmountViewModel.fiatAmount/sharedViewModel.orderData.value.cryptoPrice)} ${sharedViewModel.orderData.value.cryptoSymbol}",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp,
        )


    }
}

@Composable
fun BuyButton(
    navController: NavHostController,
    buyAmountViewModel: BuyAmountViewModel,
    sharedViewModel: BuyOrSellSharedViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxWidth()
    ) {
        FloatingActionButton(
            backgroundColor =  if(
                buyAmountViewModel.fiatAmount > sharedViewModel.orderData.value.minLimit
                && buyAmountViewModel.fiatAmount < sharedViewModel.orderData.value.maxLimit
            ) {
                colorResource(id = R.color.grass_green)
            } else Color.LightGray,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 18.dp,
                focusedElevation = 18.dp
            ),
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = {
                if(
                    buyAmountViewModel.fiatAmount > sharedViewModel.orderData.value.minLimit
                    && buyAmountViewModel.fiatAmount < sharedViewModel.orderData.value.maxLimit
                ){
                    sharedViewModel.youWillPay.value = buyAmountViewModel.fiatAmount
                    sharedViewModel.youWillGet.value = buyAmountViewModel.fiatAmount/sharedViewModel.orderData.value.cryptoPrice
                    navController.navigate(Screen.BuyConfirmationScreen.route)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Buy Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}