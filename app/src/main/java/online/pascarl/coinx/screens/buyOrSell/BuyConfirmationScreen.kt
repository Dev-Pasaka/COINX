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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material.icons.outlined.SwapHorizontalCircle
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
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen


@Composable
fun BuyConfirmationScreen(
    navController:NavHostController,
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel = viewModel(),
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
            .padding(vertical = 16.dp)
            .fillMaxSize()
            .alpha(alphaAnim.value)
    ) {
        SelectPaymentMethodUpperSection(
            navController = navController,
            buyConfirmationScreenViewModel = buyConfirmationScreenViewModel,
            sharedViewModel = sharedViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectPaymentMethodMiddleSection(
            buyConfirmationScreenViewModel = buyConfirmationScreenViewModel,
            sharedViewModel = sharedViewModel
        )
        Spacer(modifier = Modifier.height(24.dp))
        SelectPaymentMethodLowerSection(
            navController = navController,
            buyConfirmationScreenViewModel,
            sharedViewModel = sharedViewModel
        )


    }
}
@Composable
fun SelectPaymentMethodUpperSection(
    navController: NavHostController,
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel,
    sharedViewModel: BuyOrSellSharedViewModel
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
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
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text =  "You will pay",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            fontSize = 16.sp,
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
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = sharedViewModel.formatCurrency(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.background),
                fontSize = 14.sp,
            )
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun SelectPaymentMethodMiddleSection(
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel,
    sharedViewModel: BuyOrSellSharedViewModel,
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10))
            .background(color = colorResource(id = R.color.background))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ){
                Text(
                    text = "P2P Trading ",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.SwapHorizontalCircle,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sharedViewModel.orderData.value.username,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 12.sp,
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
                    text = "You bought ",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = String.format("%.8f",sharedViewModel.youWillGet.value) +
                        " ${sharedViewModel.orderData.value.cryptoSymbol}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 12.sp,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .clickable {

                }
        ) {
            Text(
                text = "How to buy ",

                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
            )
            Icon(
                imageVector = Icons.Outlined.PlayCircleFilled,
                contentDescription = null,
                tint = Color.White
            )
        }

    }
}
@Composable
fun SelectPaymentMethodLowerSection(
    navController: NavHostController,
    buyConfirmationScreenViewModel: BuyConfirmationScreenViewModel,
    sharedViewModel: BuyOrSellSharedViewModel
){
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
                        color = colorResource(id = R.color.grass_green)
                    )
            ) {
                Text(
                    text = "Buy",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = imageLoader(symbol = sharedViewModel.orderData.value.cryptoSymbol),
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
                text = "Fiat Amount",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Text(
                text = sharedViewModel.formatCurrency(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 12.sp,
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
                text = "Price",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Text(
                text = sharedViewModel.formatCurrency(amount = sharedViewModel.orderData.value.cryptoPrice),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 12.sp,
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
                text = "Crypto Amount",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Text(
                text = "${sharedViewModel.youWillGet.value} ${sharedViewModel.orderData.value.cryptoSymbol}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 1.dp,
            color = Color.LightGray,
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
                    text = "You will pay",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                Text(
                    text = sharedViewModel.formatCurrency(),
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 16.sp,
                )
            }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = colorResource(id = R.color.background))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ){
                        navController.navigate(Screen.BuyOrderCreationScreen.route)
                    }
            ) {
                Text(
                    text = "Confirm",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}