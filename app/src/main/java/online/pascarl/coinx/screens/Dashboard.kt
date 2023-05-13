
package online.pascarl.coinx.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import online.pascarl.coinx.*
import online.pascarl.coinx.R
import online.pascarl.coinx.datasource.expressCheckOut
import java.util.*



@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ProduceStateDoesNotAssignValue", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Dashboard(
    navController:NavHostController

){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.app_white))
            .fillMaxSize()
    ) {
        TopBarComponents()
            if(isInternetAvailable(context = context)){
                FetchCryptoPrices.loadData = expressCheckOut()
                Column {
                    Salutation()
                    WalletCardComposable()
                    ExpressCheckout()
                    CoinsOrWatchList()
                }
            }else{
                NoInternet()
            }
    }



}




/*@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun Preview(){
    Dashboard()
}*/

@Composable
fun TopBarComponents(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.person_icon),
            tint = Color.Gray,
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(360.dp))
                .background(color = Color.LightGray)
                .clickable {

                }
        )

        Spacer(modifier = Modifier.width(50.dp))

        Row {

            Icon(
                painter = painterResource(id = R.drawable.qr_code_scanner),
                contentDescription = "Profile Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Outlined.Notifications,
                contentDescription = "Profile Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable {

                    },

                )
        }


    }
}
@Composable
fun Salutation(username:String = "Pasaka"){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {

            Column(
            ){

                val salutation by remember { mutableStateOf(getCurrentTime())}
                Text(
                    text = "$salutation,",
                    color = colorResource(id = R.color.background),
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp,
                )
                Text(
                    text = "$username",
                    //color = colorResource(id = R.color.),
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                )
            }
        }

}

@Composable
fun WalletCardComposable(currencySymbol: String = "KES", balance: Double = 54700.0){

    val time by remember { mutableStateOf(getCurrentTime()) }
    val totalAmount = formatCurrency(symbol = currencySymbol, value = balance)
    var hideBalance by rememberSaveable{
        mutableStateOf(true)

    }
    var hideBalanceAmount by remember{ mutableStateOf(0) }
    var imageVictor:ImageVector by remember { mutableStateOf(Outlined.VisibilityOff) }

    val color = when(time){
        "Good morning" ->colorResource(id = R.color.grass_green)
        "Good afternoon" -> colorResource(id = R.color.purple_200)
        else -> {
            colorResource(id = R.color.orange)
        }
    }



    val gradient = linearGradient(
        0.0f to colorResource(id = R.color.background),
        500.0f to color ,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Column(
       // verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(gradient)
    ) {
       Row(
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier
               .fillMaxWidth()
               .padding(start = 16.dp, end = 16.dp)
       ) {
           Text(
               text = "Portfolio Balance",
               style = MaterialTheme.typography.body1,
               fontSize = 15.sp,
               fontWeight = FontWeight.W500,
               color = colorResource(id = R.color.app_white)
           )

           IconButton(
               onClick = {
                   if (hideBalance){
                       imageVictor = Outlined.Visibility
                       hideBalance = false
                       hideBalanceAmount = 25
                   }else{
                       imageVictor = Outlined.VisibilityOff
                       hideBalance = true
                       hideBalanceAmount = 0

                   }
               }
           ) {
               Icon(
                   imageVector = imageVictor,
                   contentDescription = "Profile Icon",
                   tint = colorResource(id = R.color.app_white) ,
               )
           }
       }

            Text(
                text = if (hideBalance) totalAmount else "KES ****",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = colorResource(id = R.color.app_white),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)

            )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            /**Buy Icon*/
            IconButton(
                onClick = {

                }
            ) {
                Column {
                    Icon(
                        imageVector = Outlined.ThumbUp,
                        contentDescription = "Buy Icon",
                        tint = colorResource(id = R.color.app_white) ,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Buy",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.app_white),
                    )
                }
            }
            /**Pay Icon*/
            IconButton(
                onClick = {

                }
            ) {
                Column {
                    Icon(
                        imageVector = Outlined.CreditCard,
                        contentDescription = "Pay Icon",
                        tint = colorResource(id = R.color.app_white) ,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pay",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.app_white),
                    )
                }
            }
            /**Send Icon*/
            IconButton(
                onClick = {

                }
            ) {
                Column {
                    Icon(
                        imageVector = Outlined.Send,
                        contentDescription = "Send Icon",
                        tint = colorResource(id = R.color.app_white) ,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Send",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.app_white),
                    )
                }
            }
            /**Sell Icon*/
            IconButton(
                onClick = {

                }
            ) {
                Column {
                    Icon(
                        imageVector = Outlined.Payments,
                        contentDescription = "Profile Icon",
                        tint = colorResource(id = R.color.app_white) ,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Sell",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.app_white),
                    )
                }
            }

        }
    }
}


@OptIn( ExperimentalPagerApi::class)
@Composable
fun ExpressCheckout(){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 16.dp, end=16.dp, top=5.dp, bottom = 5.dp)
    )
    {
        Text(
            text = "Express Checkout",
            style = MaterialTheme.typography.body2,
        )
        //Spacer(modifier = Modifier.height(5.dp))
        val context = LocalContext.current
        val data = FetchCryptoPrices.loadData
        val expressCheckOutList by remember {
           mutableStateOf(data)
       }

        val pagerState = rememberPagerState()
        HorizontalPager(
            count = 4,
            state = pagerState,

        ) {
            /**Auto scroller */

            LaunchedEffect(key1 = pagerState.currentPage) {
                launch {
                    delay(3000)
                    with(pagerState) {
                        val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                        animateScrollToPage(
                            page = target,
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = LinearOutSlowInEasing
                            )
                        )
                    }
                }
            }

            pagerState.currentPage
                ExpressCheckOutItems(
                    name = expressCheckOutList[it].name,
                    symbol = expressCheckOutList[it].symbol,
                    imageIcon = imageLoader(expressCheckOutList[it].symbol),
                    price =  expressCheckOutList[it].price,
                    percentageChangeIn24Hrs = expressCheckOutList[it].percentageChangeIn24Hrs,
                    firstGradientColor = expressCheckOutList[it].firstGradientColor,
                    modifier = Modifier.clickable {
                        showMessage(context, "Buying ${expressCheckOutList[it].name}")
                    }
                )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}


@Composable
fun ExpressCheckOutItems(

    name: String = "Bitcoin",
    symbol: String = "BTC",
    imageIcon:Painter = painterResource(id = R.drawable.bitcoin_icon),
    price:Double =3948175.55,
    percentageChangeIn24Hrs:Double = -1.58,
    firstGradientColor:Color = colorResource(id = R.color.orange),
    secondGradientColor:Color = colorResource(id = R.color.black),
    modifier:Modifier = Modifier

){
    val gradient = linearGradient(
        0.01f to secondGradientColor,
        50.0f to firstGradientColor,
        start = Offset.Zero,
        end = Offset.Infinite
    )


    Column(
        modifier = modifier
            .requiredWidth(360.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(brush = gradient)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ){
                Image(
                    painter = imageIcon,
                    contentDescription = "Bitcoin",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(360.dp))
                )
                Text(
                    text = "$name ($symbol)",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W400,
                    color = colorResource(id = R.color.app_white),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$percentageChangeIn24Hrs %",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W300,
                    color = if (percentageChangeIn24Hrs < 0.0)
                        colorResource(id = R.color.red) else colorResource(id = R.color.yellow_green)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "last 24 hrs",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.W300,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.app_white),
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Last Price",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.W300,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.app_white),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = formatCurrency("KES", price),
                    style = MaterialTheme.typography.body1,
                    //   fontSize = 14.sp,
                    color = colorResource(id = R.color.app_white),
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .height(30.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(color = colorResource(id = R.color.app_white))

            ) {
                Text(
                    text = "Buy Now",
                    style = MaterialTheme.typography.h3,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun CoinsOrWatchList(){
    var isCoinSelected by rememberSaveable { mutableStateOf(true) }
    var isWatchListSelected by rememberSaveable{ mutableStateOf(false) }
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp,)
        ) {
            Row(){
                Text(
                    text = "Coin",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = if (isCoinSelected) colorResource(id = R.color.background) else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable {
                            isCoinSelected = true
                            isWatchListSelected = false

                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Watchlist",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = if (isWatchListSelected) colorResource(id = R.color.background) else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable {
                            isWatchListSelected = true
                            isCoinSelected = false
                        }
                )
            }
            Spacer(modifier = Modifier.width(105.dp))
            Column {
                Text(
                    text = "See all",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body1,
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    color =  colorResource(id = R.color.background),
                    modifier = Modifier
                        .padding(top = 8.dp, end = 16.dp)
                        .clickable {

                        }
                )
            }

        }
        FilterChips(isCoinSelected = isCoinSelected )

    }
}

@Composable
fun FilterChips(isCoinSelected:Boolean){
    var isMarketCapSelected by rememberSaveable { mutableStateOf(true) }
    var isPriceSelected by rememberSaveable{ mutableStateOf(false) }
    var is24HrChangeSelected by rememberSaveable{ mutableStateOf(false) }
    val list = FetchCryptoPrices.loadData.sortedBy { it.marketCap }.asReversed()
    var cryptoList by remember {
        mutableStateOf(list)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .alpha(if (isCoinSelected) 1f else 0f)
    ) {
        Text(
            text = "Market Cap",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(30))
                .background(if (isMarketCapSelected) Color.Gray else Color.Transparent)
                .clickable {
                    isMarketCapSelected = true
                    isPriceSelected = false
                    is24HrChangeSelected = false

                    cryptoList = cryptoList
                        .sortedBy {
                            it.marketCap
                        }
                        .asReversed()
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Price",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(30))
                .background(if (isPriceSelected) Color.Gray else Color.Transparent)
                .clickable {
                    isPriceSelected = true
                    isMarketCapSelected = false
                    is24HrChangeSelected = false
                    cryptoList = cryptoList
                        .sortedBy {
                            it.price
                        }
                        .asReversed()
                }
        )

        Text(
            text = "24H Change",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.black),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(30))
                .background(if (is24HrChangeSelected) Color.Gray else Color.Transparent)
                .clickable {
                    is24HrChangeSelected = true
                    isPriceSelected = false
                    isMarketCapSelected = false

                    cryptoList = cryptoList
                        .sortedBy {
                            it.percentageChangeIn24Hrs
                        }
                        .asReversed()
                }
        )
    }

    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxWidth()){

        items(cryptoList.size){
            CryptoCoinListItem(
                name = cryptoList[it].name,
                symbol = cryptoList[it].symbol,
                imageIcon = imageLoader(symbol = cryptoList[it].symbol),
                percentageChangeIn24Hrs = cryptoList[it].percentageChangeIn24Hrs,
                price = cryptoList[it].price,
                modifier = Modifier.clickable {
                    showMessage(context, cryptoList[it].name)
                }
            )
        }
    }
}

@Composable
fun CryptoCoinListItem(
    name:String = "Bitcoin",
    symbol: String = "BTC",
    imageIcon: Painter = painterResource(id = R.drawable.bitcoin_icon),
    percentageChangeIn24Hrs: Double,
    price: Double,
    modifier: Modifier = Modifier
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 5.dp)
            .fillMaxWidth()

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imageIcon,
                contentDescription = "Bitcoin",
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .clip(RoundedCornerShape(360.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black),
                )
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.black),
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$percentageChangeIn24Hrs%",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color =  if (percentageChangeIn24Hrs < 0.0)
                    colorResource(id = R.color.red) else colorResource(id = R.color.grass_green)
            )
            Text(
                text = formatCurrency("KES", price),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = colorResource(id = R.color.black),
            )
        }
    }

}


