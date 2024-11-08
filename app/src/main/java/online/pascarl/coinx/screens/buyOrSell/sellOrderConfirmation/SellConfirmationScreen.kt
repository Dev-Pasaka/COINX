package online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation

import android.app.Application
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material.icons.outlined.SwapHorizontalCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.SellAmountViewModel
import online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model.CreateSellOrderDto


@Preview(showSystemUi = true )
@Composable
fun SellConfirmationScreenPreview(){
    val navController = rememberNavController()
    SellConfirmationScreen(navController = navController)
}
@Composable
fun SellConfirmationScreen(
    navController: NavHostController,
    sellAmountViewModel: SellAmountViewModel = viewModel(),
    sharedViewModel: BuyCryptoSharedViewModel = viewModel(),
) {
    val context = LocalContext.current
    val sellOrderConfirmationViewModel = viewModel<SellOrderConfirmationViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SellOrderConfirmationViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )
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
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp)
            .fillMaxSize()
            .alpha(alphaAnim.value)
    ) {
        SellPaymentMethodUpperSection(
            navController = navController,
            sharedViewModel = sharedViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        SellPaymentMethodMiddleSection(sharedViewModel = sharedViewModel)
        Spacer(modifier = Modifier.height(24.dp))
        SellPaymentMethodLowerSection(
            navController = navController,
            sharedViewModel = sharedViewModel,
            sellOrderConfirmationViewModel = sellOrderConfirmationViewModel
        )


    }
}
@Composable
fun SellPaymentMethodUpperSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(360.dp))

            )

        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text =  "You will receive",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = sharedViewModel.formatCurrency(amount = sharedViewModel.youWillReceive),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
@Composable
fun SellPaymentMethodMiddleSection(sharedViewModel: BuyCryptoSharedViewModel){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "P2P Trading ",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(
                    imageVector = Icons.Outlined.SwapHorizontalCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = sharedViewModel.sellAdData?.merchantUsername ?: "",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sold amount",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = sharedViewModel.youSold.toString() +
                        " ${sharedViewModel.sellAdData?.cryptoSymbol}",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "How to Sell ",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
            Icon(
                imageVector = Icons.Outlined.PlayCircleFilled,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

    }
}
@Composable
fun SellPaymentMethodLowerSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel,
    sellOrderConfirmationViewModel: SellOrderConfirmationViewModel
){
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomEnd = 30.dp, topEnd = 30.dp))
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
            ) {
                Text(
                    text = "Sell",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = imageLoader(symbol = sharedViewModel.sellAdData?.cryptoSymbol ?: ""),
                contentDescription = "Crypto symbol",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(25.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Fiat Amount ",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = sharedViewModel.formatCurrency(amount = sharedViewModel.youWillReceive),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Price",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text =sharedViewModel.formatCurrency(amount = sharedViewModel.sellAdData?.cryptoPrice ?: 0.0),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = "Sold amount",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = sharedViewModel.youSold.toString() +
                        " ${sharedViewModel.sellAdData?.cryptoSymbol}",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ){
            Column(
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = "You will receive",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = sharedViewModel.formatCurrency(amount = sharedViewModel.youWillReceive),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            if (sellOrderConfirmationViewModel.orderStatus.orderIsCreating){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .size(15.dp)
                )
            }else {

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            scope.launch {
                                sellOrderConfirmationViewModel.createSellOrder(
                                    sellOrderDto = CreateSellOrderDto(
                                        adId = sharedViewModel.sellAdData?.id ?: "",
                                        cryptoName = sharedViewModel.sellAdData?.cryptoName ?: "",
                                        cryptoSymbol = sharedViewModel.sellAdData?.cryptoSymbol ?: "",
                                        cryptoAmount = sharedViewModel.youSold
                                    )
                                )
                                if (sellOrderConfirmationViewModel.orderStatus.orderCreated){
                                    navController.navigate(Screen.SellOrderCreationScreen.route)
                                }
                            }

                        }
                ) {
                    Text(
                        text = "Confirm",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}