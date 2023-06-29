package online.pascarl.coinx.screens.bottom_bar_navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Backspace
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.model.Ads

@Preview(showSystemUi = true)
@Composable
fun BuyCryptosSectionPreview(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel = viewModel()) {
    val navController = rememberNavController()
    BuyOrSellCryptos(navController = navController, buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
    //EnterAmountToBuyOrSell(buyOrSellCryptosViewModel)

}

@Composable
fun BuyOrSellCryptos(
    navController: NavHostController,
    buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel = viewModel()
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
    }
    when(buyOrSellCryptosViewModel.isEnterAmountScreenOpen){
        true -> EnterAmountToBuyOrSell(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        else ->Column(modifier = Modifier.alpha(alphaAnim.value)) {
            BuyOrSellCryptoHeader(navController = navController)
            BuyOrSellToggle(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            SubHeader()
            BuyOrSellCryptosBody(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        }
    }

}
@Composable
fun BuyOrSellCryptoHeader(navController: NavController){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "Go Back",
            tint = Color.Gray,
            modifier = Modifier
                .size(15.dp)
                .clip(RoundedCornerShape(360.dp))
                .clickable {
                    navController.popBackStack()
                }
        )

    }
}
@Composable
fun BuyOrSellCryptosBody(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
    LazyColumn{
        items(buyOrSellCryptosViewModel.finalListOfAds.size){
            OrderAdsItem(
                buyOrSellCryptosViewModel = buyOrSellCryptosViewModel,
                cryptoAd = buyOrSellCryptosViewModel.finalListOfAds[it]
            )
        }
    }
}
@Composable
fun SubHeader(){
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
fun OrderAdsItem(cryptoAd: Ads, buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel) {
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
                        .clickable {
                            buyOrSellCryptosViewModel.enterAmountScreenAdType = cryptoAd.adType
                            buyOrSellCryptosViewModel.enterAmountScreenCryptoName =
                                cryptoAd.cryptoName
                            buyOrSellCryptosViewModel.enterAmountScreenCryptoSymbol =
                                cryptoAd.cryptoSymbol
                            buyOrSellCryptosViewModel.enterAmountScreenCryptoPrice =
                                cryptoAd.cryptoPrice

                            buyOrSellCryptosViewModel.enterAmountScreenPaymentMethod =
                                cryptoAd.paymentMethod
                            when(buyOrSellCryptosViewModel.isBuySelected){
                                "Buy" ->{
                                    buyOrSellCryptosViewModel.enterAmountScreenCryptoBuyMaxLimit =
                                        cryptoAd.maxOrder
                                    buyOrSellCryptosViewModel.enterAmountScreenCryptoBuyMinLimit =
                                        cryptoAd.minOrder

                                }
                                "Sell" ->{
                                    buyOrSellCryptosViewModel.enterAmountScreenCryptoSellMaxLimit =
                                        cryptoAd.maxOrder
                                    buyOrSellCryptosViewModel.enterAmountScreenCryptoSellMinLimit =
                                        cryptoAd.minOrder
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
                        .clickable {
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
                Spacer(modifier = Modifier.width(16.dp))
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
                        .clickable {
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
                .clickable {
                    buyOrSellCryptosViewModel.openAndCloseDropdown()
                }

        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal =  10.dp, vertical = 10.dp)

            ){
                Image(
                    painter = imageLoader( symbol = buyOrSellCryptosViewModel.selectedItem),
                    contentDescription = "Crypto symbol" ,
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
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Crypto symbol" ,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
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
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Image(
                            painter = imageLoader( symbol = it.symbol),
                            contentDescription = "Crypto symbol" ,
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


@Composable
fun EnterAmountToBuyOrSell(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.1f)
    )
    LaunchedEffect(key1 = true){
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
    ){
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
                ){

                    Icon(
                        imageVector = Icons.Default.ArrowBackIos,
                        contentDescription = "Go Back",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(15.dp)
                            .clip(RoundedCornerShape(360.dp))
                            .clickable {
                                buyOrSellCryptosViewModel.openOrCloseEnterAmountScreen()
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${buyOrSellCryptosViewModel.enterAmountScreenAdType} " +
                                buyOrSellCryptosViewModel.enterAmountScreenCryptoName,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = when(buyOrSellCryptosViewModel.isBuySelected) {
                            "Buy" -> {
                                "Limit ${buyOrSellCryptosViewModel.enterAmountScreenCryptoBuyMinLimit}" +
                                        " - ${buyOrSellCryptosViewModel.enterAmountScreenCryptoBuyMaxLimit}"

                            }
                            "Sell" -> {
                                "Limit ${buyOrSellCryptosViewModel.enterAmountScreenCryptoSellMinLimit}" +
                                        " - ${buyOrSellCryptosViewModel.enterAmountScreenCryptoSellMaxLimit}"
                            }
                            else ->""
                        },
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.background),
                        fontSize = 12.sp,
                    )
                }
            }
            when(buyOrSellCryptosViewModel.isBuySelected){
                "Buy" ->{
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
                            text = buyOrSellCryptosViewModel.enterAmountScreenPaymentMethod,
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center,
                            color = colorResource(id = R.color.background),
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }

        AmountInput(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        NumberPad(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        BuyOrSellButton(buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)

    }
}
@Composable
fun NumberPad(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
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
            NumberKey(numberKey = "1", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "2", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "3", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            NumberKey(numberKey = "4", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "5", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "6", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            NumberKey(numberKey = "7", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "8", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "9", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            NumberKey(numberKey = ".", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            NumberKey(numberKey = "0", buyOrSellCryptosViewModel = buyOrSellCryptosViewModel)
            Column(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        when (buyOrSellCryptosViewModel.isBuySelected) {
                            "Buy" -> buyOrSellCryptosViewModel.cryptoBuyAmount =
                                buyOrSellCryptosViewModel.removeLastCharacter(
                                    input = buyOrSellCryptosViewModel.cryptoBuyAmount
                                )

                            else -> buyOrSellCryptosViewModel.cryptoSellAmount =
                                buyOrSellCryptosViewModel.removeLastCharacter(
                                    input = buyOrSellCryptosViewModel.cryptoSellAmount
                                )
                        }
                    }
            ){
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
fun NumberKey(numberKey:String = "1", buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable {
                when (buyOrSellCryptosViewModel.isBuySelected) {
                    "Buy" -> buyOrSellCryptosViewModel.cryptoBuyAmount += numberKey
                    else -> buyOrSellCryptosViewModel.cryptoSellAmount += numberKey
                }
            }
    ){
        Column(modifier = Modifier.padding(16.dp)){
            Text(
                text = numberKey,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 32.sp,
            )
        }
    }
}
@Composable
fun AmountInput(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = when(buyOrSellCryptosViewModel.isBuySelected){
                "Buy" -> "KSH ${buyOrSellCryptosViewModel.cryptoBuyAmount}"
                else -> "AMT ${buyOrSellCryptosViewModel.cryptoSellAmount}"
            },
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            fontSize = 40.sp,
        )
        Text(
            text = "1 ${buyOrSellCryptosViewModel.enterAmountScreenCryptoSymbol} " +
                    buyOrSellCryptosViewModel.enterAmountScreenCryptoPrice,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp,
        )
        Text(
            text = when(buyOrSellCryptosViewModel.isBuySelected){
                "Buy" -> "${String.format("%.8f", buyOrSellCryptosViewModel.cryptoBuyAmountDouble)} " +
                        buyOrSellCryptosViewModel.enterAmountScreenCryptoSymbol
                else -> "${String.format("%.8f", buyOrSellCryptosViewModel.cryptoSellAmountDouble)} " +
                        buyOrSellCryptosViewModel.enterAmountScreenCryptoSymbol
            },
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp,
        )


    }
}
@Composable
fun BuyOrSellButton(buyOrSellCryptosViewModel: BuyOrSellCryptosViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        FloatingActionButton(
            backgroundColor = if(
                buyOrSellCryptosViewModel.isBuySelected == "Buy"
                    ){
                colorResource(id = R.color.grass_green)
            }else if(
                buyOrSellCryptosViewModel.isBuySelected == "Sell"
            ){
                Color.Red
            }
            else Color.Gray,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 18.dp,
                focusedElevation = 18.dp
            ),
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = {  }
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