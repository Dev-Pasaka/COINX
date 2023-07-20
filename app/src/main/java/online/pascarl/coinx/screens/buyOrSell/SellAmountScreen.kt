package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
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
fun SellAmountScreen(
    navController:NavHostController,
    sellAmountViewModel: SellAmountViewModel = viewModel(),
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
            .background(color = MaterialTheme.colorScheme.background)
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = sharedViewModel.orderData.value.adsType,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
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
                        text = "Limit ${sharedViewModel.orderData.value.minLimit} - ${sharedViewModel.orderData.value.maxLimit}",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }

        SellAmountInput(sellAmountViewModel = sellAmountViewModel, sharedViewModel = sharedViewModel)
        SellNumberPad(sellAmountViewModel = sellAmountViewModel)
        SellButton(
            sellAmountViewModel = sellAmountViewModel,
            sharedViewModel = sharedViewModel,
            navController = navController
        )

    }
}

@Composable
fun SellNumberPad(sellAmountViewModel: SellAmountViewModel) {
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
            SellNumberKey(numberKey = "1", sellAmountViewModel = sellAmountViewModel )
            SellNumberKey(numberKey = "2", sellAmountViewModel = sellAmountViewModel )
            SellNumberKey(numberKey = "3", sellAmountViewModel = sellAmountViewModel )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            SellNumberKey(numberKey = "4", sellAmountViewModel = sellAmountViewModel )
            SellNumberKey(numberKey = "5", sellAmountViewModel = sellAmountViewModel )
            SellNumberKey(numberKey = "6", sellAmountViewModel = sellAmountViewModel )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            SellNumberKey(numberKey = "7", sellAmountViewModel = sellAmountViewModel)
            SellNumberKey(numberKey = "8", sellAmountViewModel = sellAmountViewModel)
            SellNumberKey(numberKey = "9", sellAmountViewModel = sellAmountViewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            SellNumberKey(numberKey = ".", sellAmountViewModel = sellAmountViewModel)
            SellNumberKey(numberKey = "0", sellAmountViewModel = sellAmountViewModel)
            Column(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .padding(12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ){
                        sellAmountViewModel.cryptoSellAmount =
                            sellAmountViewModel.removeLastCharacter(
                                input = sellAmountViewModel.cryptoSellAmount
                            )
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Backspace,
                    contentDescription = "Back Space",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

        }

    }
}

@Composable
fun SellNumberKey(numberKey: String = "1", sellAmountViewModel: SellAmountViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
            ){
                sellAmountViewModel.cryptoSellAmount += numberKey
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = numberKey,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun SellAmountInput(sellAmountViewModel: SellAmountViewModel, sharedViewModel: BuyOrSellSharedViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "AMT ${sellAmountViewModel.fiatAmount}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = "1 ${sharedViewModel.orderData.value.cryptoSymbol} = ${sharedViewModel.orderData.value.cryptoPrice} KES",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = sellAmountViewModel.formatCurrency(
                amount = sellAmountViewModel.fiatAmount*sharedViewModel.orderData.value.cryptoPrice
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
        )


    }
}

@Composable
fun SellButton(
    navController:NavHostController,
    sellAmountViewModel: SellAmountViewModel,
    sharedViewModel: BuyOrSellSharedViewModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = {
                if (
                    sellAmountViewModel.fiatAmount > sharedViewModel.orderData.value.minLimit
                    && sellAmountViewModel.fiatAmount < sharedViewModel.orderData.value.maxLimit
                ){
                    sharedViewModel.youWillReceive.value = sellAmountViewModel.fiatAmount*sharedViewModel.orderData.value.cryptoPrice
                    sharedViewModel.youSold.value = sellAmountViewModel.fiatAmount
                    navController.navigate(Screen.SellConfirmationScreen.route)
                }

            }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Buy Icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}
