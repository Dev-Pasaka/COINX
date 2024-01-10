package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components.BuyAmountInput
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components.BuyButton
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components.BuyNumberPad


@Composable
fun BuyAmountScreen(
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel,
    navController: NavHostController,
) {
    val buyAmountViewModel = viewModel<BuyAmountViewModel>()
    buyAmountViewModel.buyAdData = buyCryptoSharedViewModel.buyAdData
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
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
            .background(color = MaterialTheme.colorScheme.background)
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
                        text = "",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = imageLoader(symbol = buyAmountViewModel.buyAdData?.cryptoSymbol ?: ""),
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
                        text = "Limit ${buyAmountViewModel.buyAdData?.minLimit} - ${buyAmountViewModel.buyAdData?.maxLimit}" ,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Payment Method",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(3.dp))
                if (buyAmountViewModel.buyAdData?.paymentMethod?.mpesaSafaricom != null){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "M-Pesa Safaricom" ,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SelectionContainer {
                                Text(
                                    text = buyAmountViewModel.buyAdData?.paymentMethod?.mpesaSafaricom!!.phone ,
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
                                    .size(15.dp).clickable {
                                        buyAmountViewModel.copyToClipboard(
                                            context = context,
                                            text = buyAmountViewModel.buyAdData?.paymentMethod?.mpesaSafaricom!!.phone
                                        )
                                    }

                            )
                        }

                    }

                }


            }
        }
        BuyAmountInput(buyAmountViewModel = buyAmountViewModel)
        BuyNumberPad(
            navController = navController,
            buyAmountViewModel = buyAmountViewModel
        )
        BuyButton(
            navController = navController,
            buyAmountViewModel = buyAmountViewModel
        ) {
            if (
                buyAmountViewModel.fiatAmount > (buyAmountViewModel.buyAdData?.minLimit?.times(
                    buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0
                ) ?: 0.0)
                && buyAmountViewModel.fiatAmount < (buyAmountViewModel.buyAdData?.maxLimit?.times(
                    buyAmountViewModel.buyAdData?.cryptoPrice ?: 0.0
                ) ?: 0.0)
            ) {
                buyCryptoSharedViewModel.updateCryptoAmountAndFiatAmount(
                    fiatAmount = buyAmountViewModel.youWillPay,
                    cryptoAmount = buyAmountViewModel.youWillGet
                )
                navController.navigate(Screen.BuyConfirmationScreen.route)
            }

        }

    }
}
