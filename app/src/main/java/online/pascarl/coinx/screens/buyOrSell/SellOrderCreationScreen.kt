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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.model.OrderCreatedModel
import online.pascarl.coinx.navigation.SELECTEDINDEX
import online.pascarl.coinx.navigation.Screen


@Preview(showSystemUi = true)
@Composable
fun SellOrderCreationPreview() {
    val navController = rememberNavController()
    SellOrderCreationScreen(navController = navController)
}


@Composable
fun SellOrderCreationScreen(
    navController: NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel = viewModel(),
    orderCreatedModel: OrderCreatedModel = OrderCreatedModel(
        cryptoSymbol = sharedViewModel.orderData.value.cryptoSymbol,
        merchantUsername = sharedViewModel.orderData.value.username
    )
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
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .alpha(alphaAnim.value)
    ) {
        SellOrderCreationTopSection(navController = navController,orderCreatedModel = orderCreatedModel)
        SellOrderCreationMiddleSection(navController = navController,orderCreatedModel = orderCreatedModel)
        SellOrderCreationBottomSection(navController = navController,orderCreatedModel = orderCreatedModel)
    }

}

@Composable
fun SellOrderCreationTopSection(
    navController: NavHostController,
    orderCreatedModel: OrderCreatedModel
) {
    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
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
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)
                        .clip(RoundedCornerShape(360.dp))

                )

            }
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = imageLoader(symbol = orderCreatedModel.cryptoSymbol ),
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
                        .background(color = Color.Red)
                ) {
                    Text(
                        text = "Sell",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
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
                color = Color.DarkGray,
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
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                Text(
                    text = "15 mins",
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.orange),
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
fun SellOrderCreationMiddleSection(
    navController: NavHostController,
    orderCreatedModel: OrderCreatedModel
) {
    Column{
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(10))
                .background(color = colorResource(id = R.color.background))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)


                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                shape = CircleShape,
                                color = colorResource(id = R.color.orange)
                            )
                    ) {
                        Text(
                            text = orderCreatedModel.merchantUsername.firstOrNull().toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            color = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = orderCreatedModel.merchantUsername,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .clickable {
                                //navController.popBackStack()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Go Back",
                            tint = Color.White,
                            modifier = Modifier
                                .size(15.dp)
                                .clip(RoundedCornerShape(360.dp))

                        )

                    }
                }
                Spacer(modifier = Modifier.width(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = "Chat icon",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(18.dp)
                    )
                    Text(
                        text = " Chat",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                    )
                }


            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(15.dp)
                            .background(
                                shape = CircleShape,
                                color = colorResource(id = R.color.grass_green)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check icon",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Coinx is holding the your crypto in the escrow account",
                        textAlign = TextAlign.Justify,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                    )

                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(end = 16.dp)
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(15.dp)
                            .background(
                                shape = CircleShape,
                                color = colorResource(id = R.color.grass_green)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check icon",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Coinx 24/7 customer support",
                        textAlign = TextAlign.Justify,
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier
                    )

                }
            }


        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8))
                .background(color = colorResource(id = R.color.light_orange))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            shape = CircleShape,
                            color = colorResource(id = R.color.orange)
                        )
                ) {
                    Text(
                        text = "!",
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "For security reasons, only use your own account to transfer funds to the" +
                            "seller. Payments made from accounts not matching your verified KYC name" +
                            " will not be accepted.",
                    textAlign = TextAlign.Justify,
                    color = colorResource(id = R.color.orange),
                    fontSize = 12.sp,
                    modifier = Modifier
                )
            }
        }
    }

}

@Composable
fun SellOrderCreationBottomSection(
    navController: NavHostController,
    orderCreatedModel: OrderCreatedModel
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8))
            .background(color = colorResource(id = R.color.light_gray))
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ){
            Text(
                text = "Terms",
                textAlign = TextAlign.Justify,
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            Text(
                text = "Strictly terms and conditions to apply Do not mark order as paid before the " +
                        "payment to the seller.",
                textAlign = TextAlign.Justify,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Read more",
                textAlign = TextAlign.Justify,
                color = colorResource(id = R.color.background),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .background(color = colorResource(id = R.color.light_gray))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    navController.navigate(
                        route = Screen.BuyOrSellCryptos.route
                    ) {
                        popUpTo(Screen.BuyOrSellCryptos.route) { inclusive = true }
                    }
                }
        ) {
            Text(
                text = "Cancel",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .background(color = colorResource(id = R.color.background))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    navController.navigate(Screen.Orders.route){
                        SELECTEDINDEX = 3
                        popUpTo(Screen.Orders.route){inclusive = true}
                    }
                }
        ) {
            Text(
                text = "Track Order",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
}