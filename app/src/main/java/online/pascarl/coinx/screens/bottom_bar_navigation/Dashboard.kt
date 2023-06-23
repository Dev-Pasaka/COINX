package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.*
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.NavigationDrawer
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.NoInternet
import online.pascarl.coinx.screens.auth_screen.showMessage


/*@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun Preview1(){
    Dashboard()
}*/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Dashboard(
    bottomBarViewModel: BottomBarViewModel = viewModel(),
    dashboardViewModel: DashboardViewModel = viewModel(),
    navController: NavHostController

    ) {
    val context = LocalContext.current
    val isNetworkAvailable  = isInternetAvailable(context = context)
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
    }

    val scaffoldState = rememberScaffoldState()
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(
            UserDatabase.getInstance(LocalContext.current.applicationContext).userDao()
        )
    )

    LaunchedEffect(Unit) {
        if(isNetworkAvailable)
            dashboardViewModel.roomUser = roomDB.getUser("12345678") ?: RoomUser()
            dashboardViewModel.getCryptoPrices()
            dashboardViewModel.cryptoPrices()
            dashboardViewModel.getUserData()
            dashboardViewModel.getUserPortfolio()
    }
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { NavigationDrawer(navController = navController) },
        bottomBar = { CustomBottomNavigation(
            navController = navController,
            bottomBarViewModel = bottomBarViewModel
        )},

    ) {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .background(color = colorResource(id = R.color.app_white))
                .fillMaxSize()
                .alpha(alphaAnim.value)
        ) {

            TopBarComponents(
                navDrawer = scaffoldState,
                dashboardViewModel = dashboardViewModel
            )
            if(isInternetAvailable(context = context)){
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Salutation(dashboardViewModel = dashboardViewModel)
                    WalletCardComposable(dashboardViewModel = dashboardViewModel)
                    ExpressCheckout(dashboardViewModel = dashboardViewModel)
                    CoinsOrWatchList(
                        navController = navController,
                        dashboardViewModel = dashboardViewModel
                    )
                }
            }else{
                NoInternet()
            }

        }

    }
}


@Composable
fun TopBarComponents(navDrawer: ScaffoldState, dashboardViewModel: DashboardViewModel) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
    ) {
        val scope = rememberCoroutineScope()
        Icon(
            painter = painterResource(id = R.drawable.person_icon),
            tint = Color.White,
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(360.dp))
                .background(color = colorResource(id = R.color.background))
                .clickable {
                    scope.launch {
                        navDrawer.drawerState.open()
                    }
                }
        )

        Spacer(modifier = Modifier.width(50.dp))

        Row {

            Icon(
                painter = painterResource(id = R.drawable.qr_code_scanner),
                contentDescription = "Profile Icon",
                tint = colorResource(id = R.color.background),
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
                tint = colorResource(id = R.color.background),
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
fun Salutation(username: String = "Pasaka", dashboardViewModel: DashboardViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Column(
        ) {

            val salutation by remember { mutableStateOf(getCurrentTime()) }
            Text(
                text = "$salutation,",
                color = colorResource(id = R.color.background),
                style = MaterialTheme.typography.body2,
                fontSize = 14.sp,
            )
            Text(
                text = dashboardViewModel.userInformation.username,
                //color = colorResource(id = R.color.),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
            )
        }
    }

}

@Composable
fun WalletCardComposable(
    currencySymbol: String = "KES",
    dashboardViewModel: DashboardViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(id = R.color.background))
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
                    dashboardViewModel.showBalance()
                }
            ) {
                Icon(
                    imageVector = if (dashboardViewModel.showBalance)
                        Outlined.Visibility else Outlined.VisibilityOff,
                    contentDescription = "Profile Icon",
                    tint = colorResource(id = R.color.app_white),
                )
            }
        }

        Text(
            text = if (dashboardViewModel.showBalance) dashboardViewModel.userBalance() else "KES ****",
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
            IconButton(onClick = { /*TODO*/ }) {
                Column {
                    Icon(
                        imageVector = Outlined.CurrencyBitcoin,
                        contentDescription = "Buy Icon",
                        tint = colorResource(id = R.color.app_white),
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Buy",
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.W300,
                        color = colorResource(id = R.color.app_white),
                    )
                }
            }

            /**Pay Icon*/
            IconButton(onClick = { /*TODO*/ }) {
                Column() {
                    Icon(
                        imageVector = Outlined.CreditCard,
                        contentDescription = "Pay Icon",
                        tint = colorResource(id = R.color.app_white),
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

            IconButton(onClick = { /*TODO*/ }) {
                Column() {
                    Icon(
                        imageVector = Outlined.Send,
                        contentDescription = "Send Icon",
                        tint = colorResource(id = R.color.app_white),
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

            IconButton(onClick = { /*TODO*/ }) {
                Column() {

                    Icon(
                        imageVector = Outlined.Payments,
                        contentDescription = "Profile Icon",
                        tint = colorResource(id = R.color.app_white),
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


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExpressCheckout(dashboardViewModel: DashboardViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp)
    )
    {
        val context = LocalContext.current
        val pagerState = rememberPagerState()
        Text(
            text = "Express Checkout",
            color = Color.Gray,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.W400,

            )
        if (dashboardViewModel.cryptoModel.isEmpty()) {
            ExpressCheckOutLoadingPreview()
        } else {
            HorizontalPager(
                count = dashboardViewModel.expressCheckoutCryptoList.size,
                state = pagerState,
            ) {
                println("number of cryptos ${dashboardViewModel.expressCheckoutCryptoList}")
                val index = it
                if (index >= 0 && index < dashboardViewModel.expressCheckoutCryptoList.size) {
                    val cryptoItem = dashboardViewModel.expressCheckoutCryptoList[index]
                    ExpressCheckOutItems(
                        name = cryptoItem.name,
                        symbol = cryptoItem.symbol,
                        imageIcon = imageLoader(cryptoItem.symbol),
                        price = cryptoItem.price,
                        percentageChangeIn24Hrs = cryptoItem.percentageChangeIn24Hrs,
                        dashboardViewModel = dashboardViewModel,
                        modifier = Modifier.clickable {
                            showMessage(context, "Buying ${cryptoItem.name}")
                        },

                        )
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    // Handle index out of bounds error if necessary
                }
            }
        }


    }
}


@Composable
fun ExpressCheckOutItems(

    name: String = "Bitcoin",
    symbol: String = "BTC",
    imageIcon: Painter = painterResource(id = R.drawable.bitcoin_icon),
    price: Double = 3948175.55,
    percentageChangeIn24Hrs: Double = -1.58,
    firstGradientColor: Color = colorResource(id = R.color.background),
    secondGradientColor: Color = colorResource(id = R.color.grass_green),
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel

) {
    val gradient = linearGradient(
        0.01f to secondGradientColor,
        50.0f to firstGradientColor,
        start = Offset.Zero,
        end = Offset.Infinite
    )


    Column(
        modifier = modifier
            .fillMaxWidth()
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
            ) {
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
                    text = dashboardViewModel.formatCurrency(amount = price),
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
fun CoinsOrWatchList(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Coin",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = if (dashboardViewModel.isCoinOrWatchlistSelected) colorResource(id = R.color.background) else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable {
                            dashboardViewModel.isCoinOrWatchlist(toggle = "coin")
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Watchlist",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = if (!dashboardViewModel.isCoinOrWatchlistSelected) colorResource(id = R.color.background) else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable {
                            dashboardViewModel.isCoinOrWatchlist(toggle = "watchlist")
                        }
                )
            }

            Text(
                text = "See all",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = colorResource(id = R.color.background),
                modifier = Modifier
                    .padding(top = 8.dp, end = 16.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable {
                        navController.navigate(Screen.SeeAllCryptos.route)
                    }

            )
        }
    }
    CryptoListOrWatchlist(dashboardViewModel = dashboardViewModel)
}


@Composable
fun FilterChips(modifier: Modifier = Modifier, dashboardViewModel: DashboardViewModel) {

    val onSelectedBackgroundColor = colorResource(id = R.color.background)
    val onNotSelectedBackgroundColor = Color.Gray

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = "market cap",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (dashboardViewModel.filterChip == "market-cap") onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    dashboardViewModel.filterCryptos("market-cap")
                    dashboardViewModel.sortCryptos("market-cap")
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "price",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (dashboardViewModel.filterChip == "price") onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    dashboardViewModel.filterCryptos("price")
                    dashboardViewModel.sortCryptos("price")
                }
        )

        Text(
            text = "24h change",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (dashboardViewModel.filterChip == "24h-change") onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    dashboardViewModel.filterCryptos("24h-change")
                    dashboardViewModel.sortCryptos("24h-change")
                }
        )
    }

    val context = LocalContext.current
    if (dashboardViewModel.cryptoModel.isEmpty()) {
        CryptoListPreview()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            items(dashboardViewModel.cryptoModel.size) {
                CryptoCoinListItem(
                    name = dashboardViewModel.cryptoModel[it].name,
                    symbol = dashboardViewModel.cryptoModel[it].symbol,
                    imageIcon = imageLoader(symbol = dashboardViewModel.cryptoModel[it].symbol),
                    percentageChangeIn24Hrs = dashboardViewModel.cryptoModel[it].percentageChangeIn24Hrs,
                    price = dashboardViewModel.cryptoModel[it].price,
                    dashboardViewModel = dashboardViewModel,
                    modifier = Modifier.clickable {
                        showMessage(context, dashboardViewModel.cryptoModel[it].name)
                    }
                )
            }
        }
    }
}

@Composable
fun CryptoCoinListItem(
    name: String = "Bitcoin",
    symbol: String = "BTC",
    imageIcon: Painter = painterResource(id = R.drawable.bitcoin_icon),
    percentageChangeIn24Hrs: Double,
    price: Double,
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 5.dp)
            .fillMaxWidth()

    ) {
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
                color = if (percentageChangeIn24Hrs < 0.0)
                    colorResource(id = R.color.red) else colorResource(id = R.color.grass_green)
            )
            Text(
                text = dashboardViewModel.formatCurrency(amount = price),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = colorResource(id = R.color.black),
            )
        }
    }

}


@Composable
fun CryptoListOrWatchlist(dashboardViewModel: DashboardViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        if (dashboardViewModel.isCoinOrWatchlistSelected) {
            FilterChips(dashboardViewModel = dashboardViewModel)
        } else if (!dashboardViewModel.isCoinOrWatchlistSelected) {
            Column() {
            }
        }
    }

}


@Composable
fun ExpressCheckOutLoadingPreview() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fetching))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.LightGray)
            .padding(vertical = 16.dp)
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
        )

    }
}

@Composable
fun CryptoListPreview() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fetching))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.LightGray)

    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
        )

    }
}





