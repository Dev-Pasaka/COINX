package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
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
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.ISCOMINGSOONSCREENON
import online.pascarl.coinx.navigation.NavDrawer
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.NoInternet
import online.pascarl.coinx.screens.auth_screen.showMessage
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.ISBUYSELECTED

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    Dashboard(navController = navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun Dashboard(
    dashboardViewModel: DashboardViewModel = viewModel(),
    navController: NavHostController

) {
    NavDrawer(navController = navController)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isNetworkAvailable = isInternetAvailable(context = context)
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }

    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(
            UserDatabase.getInstance(LocalContext.current.applicationContext).userDao()
        )
    )
    LaunchedEffect(Unit) {
        if (isNetworkAvailable)
            dashboardViewModel.roomUser = roomDB.getUser("12345678") ?: RoomUser()
        dashboardViewModel.getCryptoPrices()
        dashboardViewModel.cryptoPrices()
        dashboardViewModel.getUserData()
        dashboardViewModel.getUserPortfolio()
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                drawerContentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Coinx",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                dashboardViewModel.drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = item == dashboardViewModel.selectedDrawerItem,
                        onClick = {
                            scope.launch { drawerState.close() }
                            dashboardViewModel.selectedDrawerItem = item
                            when (item.title) {
                                "Become Merchant" -> {
                                    navController.navigate(Screen.BecomeMerchant.route) {
                                        popUpTo(Screen.BecomeMerchant.route) { inclusive = true }
                                    }
                                }

                                "Invite friends" -> {
                                    navController.navigate(Screen.InviteFriends.route) {
                                        popUpTo(Screen.InviteFriends.route) { inclusive = true }
                                    }
                                }

                                "Contact us" -> {
                                    navController.navigate(Screen.ContactUs.route) {
                                        popUpTo(Screen.ContactUs.route) { inclusive = true }
                                    }
                                }

                                "Settings" -> {
                                    navController.navigate(Screen.Settings.route) {
                                        popUpTo(Screen.Settings.route) { inclusive = true }
                                    }
                                }

                                "Logout" -> {
                                    dashboardViewModel.openDialog = true
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                bottomBar = { CustomBottomNavigation(navController = navController) },
            ) {
                val context = LocalContext.current
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .alpha(alphaAnim.value)
                ) {
                    TopBarComponents(
                        drawerState = drawerState,
                        dashboardViewModel = dashboardViewModel
                    )
                    if (isInternetAvailable(context = context)) {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            if (dashboardViewModel.openDialog) LogOutAlertDialog(
                                navController = navController,
                                dashboardViewModel = dashboardViewModel
                            )
                            Salutation(dashboardViewModel = dashboardViewModel)
                            WalletCardComposable(
                                dashboardViewModel = dashboardViewModel,
                                navController = navController
                            )
                            ExpressCheckout(dashboardViewModel = dashboardViewModel)
                            CoinsOrWatchList(
                                navController = navController,
                                dashboardViewModel = dashboardViewModel
                            )
                        }
                    } else {
                        NoInternet()
                    }

                }

            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponents(
    drawerState: DrawerState,
    dashboardViewModel: DashboardViewModel
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 13.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
    ) {
        val scope = rememberCoroutineScope()
        Icon(
            painter = painterResource(id = R.drawable.person_icon),
            tint = MaterialTheme.colorScheme.tertiary,
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(30.dp)
                .padding(3.dp)
                .clip(RoundedCornerShape(360.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    scope.launch {
                        scope.launch { drawerState.open() }
                    }
                }
        )

        Spacer(modifier = Modifier.width(50.dp))

        Row {

            Icon(
                painter = painterResource(id = R.drawable.qr_code_scanner),
                contentDescription = "Profile Icon",
                tint = MaterialTheme.colorScheme.onBackground,
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
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {

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
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = dashboardViewModel.userInformation.username,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

}

@Composable
fun WalletCardComposable(
    currencySymbol: String = "KES",
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel,
) {


    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Portfolio Balance",
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyMedium
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
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }

            Text(
                text = if (dashboardViewModel.showBalance) dashboardViewModel.userBalance() else "KES ****",
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)

            )


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                /**Buy Icon*/
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .padding(4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                            ISBUYSELECTED = "Buy"
                            navController.navigate("buy_or_sell")

                        }
                ) {
                    Icon(
                        imageVector = Outlined.CurrencyBitcoin,
                        contentDescription = "Buy Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Buy",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }

                /**Sell Icon*/
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .padding(4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                            ISBUYSELECTED = "Sell"
                            navController.navigate("buy_or_sell")
                        }
                ) {

                    Icon(
                        imageVector = Outlined.Payments,
                        contentDescription = "Profile Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Sell",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }

                /**Pay Icon*/
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .padding(4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            if (ISCOMINGSOONSCREENON) {
                                navController.navigate(route = Screen.ComingSoon.route)
                            } else {

                            }
                        }
                ) {
                    Icon(
                        imageVector = Outlined.CreditCard,
                        contentDescription = "Pay Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Pay",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }

                /**Send Icon*/
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .padding(4.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            if (ISCOMINGSOONSCREENON) {
                                navController.navigate(route = Screen.ComingSoon.route)
                            } else {

                            }
                        }
                ) {
                    Icon(
                        imageVector = Outlined.Send,
                        contentDescription = "Send Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Send",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExpressCheckout(dashboardViewModel: DashboardViewModel) {
    val configuration = LocalConfiguration.current
    val heightInDp = configuration.screenHeightDp.toFloat()
    println(heightInDp)
    if (heightInDp >= 700.0) {
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
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.W400,
                style = MaterialTheme.typography.bodySmall

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
                            imageIcon = rememberAsyncImagePainter(
                                model = cryptoItem.logoUrl
                            ),
                            price = cryptoItem.price,
                            percentageChangeIn24Hrs = cryptoItem.percentageChangeIn24Hrs,
                            dashboardViewModel = dashboardViewModel,
                            modifier = Modifier.clickable {
                                showMessage(context, "Buying ${cryptoItem.name}")
                            },

                            )
                        Spacer(modifier = Modifier.width(16.dp))

                    } else {
                        // Handle index out of bounds error if necessary
                    }
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
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(top = 3.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
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
                        fontWeight = FontWeight.W400,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.bodyMedium

                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$percentageChangeIn24Hrs %",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall

                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "last 24 hrs",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall

                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Last Price",
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = dashboardViewModel.formatCurrency(amount = price),
                        //   fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }
                Card(
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    ),
                    modifier = Modifier
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier

                    ) {
                        Text(
                            text = "Buy Now",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)

                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (dashboardViewModel.isCoinOrWatchlistSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            dashboardViewModel.isCoinOrWatchlist(toggle = "coin")
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Watchlist",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (!dashboardViewModel.isCoinOrWatchlistSelected) MaterialTheme.colorScheme.onBackground else Color.Gray,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            dashboardViewModel.isCoinOrWatchlist(toggle = "watchlist")
                        }
                )
            }

            Text(
                text = "See all",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = 8.dp, end = 16.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                        navController.navigate(Screen.SeeAllCryptos.route)
                    }

            )
        }
    }
    CryptoListOrWatchlist(dashboardViewModel = dashboardViewModel)
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(modifier: Modifier = Modifier, dashboardViewModel: DashboardViewModel) {

    val onSelectedBackgroundColor = colorResource(id = R.color.background)
    val onNotSelectedBackgroundColor = Color.Gray

    FlowRow(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(
                    color = if (dashboardViewModel.filterChip == "market-cap")
                        MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    dashboardViewModel.filterCryptos("market-cap")
                    dashboardViewModel.sortCryptos("market-cap")
                }

        ) {
            Text(
                text = "market-cap",
                color = if (dashboardViewModel.filterChip == "market-cap")
                    MaterialTheme.colorScheme.onSecondaryContainer else
                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)

            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(
                    color = if (dashboardViewModel.filterChip == "price")
                        MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    dashboardViewModel.filterCryptos("price")
                    dashboardViewModel.sortCryptos("price")
                }

        ) {
            Text(
                text = "Price",
                color = if (dashboardViewModel.filterChip == "price")
                    MaterialTheme.colorScheme.onSecondaryContainer else
                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)

            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clip(shape = MaterialTheme.shapes.medium)
                .background(
                    color = if (dashboardViewModel.filterChip == "24h-change")
                        MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    dashboardViewModel.filterCryptos("24h-change")
                    dashboardViewModel.sortCryptos("24h-change")
                }

        ) {
            Text(
                text = "24H-change",
                color = if (dashboardViewModel.filterChip == "24h-change")
                    MaterialTheme.colorScheme.onSecondaryContainer else
                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)

            )
        }


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
                    imageIcon = rememberAsyncImagePainter(model = dashboardViewModel.cryptoModel[it].logoUrl),
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
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = symbol,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$percentageChangeIn24Hrs%",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = if (percentageChangeIn24Hrs < 0.0)
                    colorResource(id = R.color.red) else colorResource(id = R.color.grass_green)
            )
            Text(
                text = dashboardViewModel.formatCurrency(amount = price),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
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
            .background(color = MaterialTheme.colorScheme.background)
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
            .background(color = MaterialTheme.colorScheme.background)

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
fun LogOutAlertDialog(navController: NavHostController, dashboardViewModel: DashboardViewModel) {
    val context = LocalContext.current
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(
            UserDatabase.getInstance(LocalContext.current.applicationContext).userDao()
        )
    )
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
            dashboardViewModel.openDialog = false
        },
        icon = {
            Icon(
                Icons.Filled.Logout,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        title = {
            Text(
                text = "Logout Confirmation",
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        text = {
            Text(
                text = "Are you sure you want to logout?",
                color = MaterialTheme.colorScheme.onSurfaceVariant,


                )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val result = roomDB.deleteUser(id = "12345678")
                    if (result > 0) {
                        showMessage(context, "Logging you out")
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }

                    }
                    dashboardViewModel.openDialog = false
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dashboardViewModel.openDialog = false
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}




