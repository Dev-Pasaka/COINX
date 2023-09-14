package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.model.UserPortfolio
import online.pascarl.coinx.nav_drawer.MerchantPaymentInformationViewModel
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.ISCOMINGSOONSCREENON
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.ISBUYSELECTED
import java.lang.Double.NaN

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Wallet(
    navController: NavHostController,
) {
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

    Scaffold(
        bottomBar = { CustomBottomNavigation(navController = navController) },
        modifier = Modifier
    ) {
        Column(modifier = Modifier
            .alpha(alphaAnim.value)

        ) {
            WalletBody(navController = navController)
        }
    }
}

@Composable
fun WalletBody(navController:NavHostController) {
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val walletViewModel = viewModel<WalletViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WalletViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = scrollState)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ){
            WalletTopSection(navController = navController, walletViewModel = walletViewModel)
            WalletMiddleSection(walletViewModel = walletViewModel)
            WalletBottomSection(walletViewModel = walletViewModel)
        }
    }
}

@Composable
fun WalletTopSection(navController: NavHostController,walletViewModel: WalletViewModel) {

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total Balance",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.Visibility,
                contentDescription = "Show Balance",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(15.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                        walletViewModel.showBalance()
                    }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if(walletViewModel.showBalance){
                    walletViewModel.formatCurrency(
                        amount = walletViewModel.userPortfolio?.balance
                            ?: 0.0
                    )
                }else{
                    "KES ****"
                     },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
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
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Deposit",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(5.dp)

                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Deposit",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
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
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Withdraw",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(5.dp)


                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Withdraw",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
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
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = "Coinx Earn",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(5.dp)

                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Coinx Earn",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }

    }

}

@Composable
fun WalletMiddleSection(walletViewModel: WalletViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Assets Allocation",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedChart(walletViewModel = walletViewModel)
    }
}

@Composable
fun WalletBottomSection(walletViewModel: WalletViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .offset(y = 100.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Your Assets",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(walletViewModel.userPortfolio.assets.isNotEmpty()) {
            Column(){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) {
                    walletViewModel.userPortfolio.assets?.size?.let { it ->
                        items(count = it) {
                            CryptoAssetItem(
                                name = walletViewModel.userPortfolio.assets[it].name,
                                symbol = walletViewModel.userPortfolio.assets[it].symbol,
                                amount = walletViewModel.userPortfolio.assets[it].amount,
                                imageIcon = rememberAsyncImagePainter(
                                    model = walletViewModel.getCryptoLogo(
                                        symbol = walletViewModel.userPortfolio.assets[it].symbol
                                    )
                                ),
                                price = walletViewModel.userPortfolio.assets[it].marketPrice,
                                walletViewModel = walletViewModel
                            )
                        }
                    }
                }
            }
        }else CryptoAssestsPreview()
    }
}


@Composable
fun CryptoAssetItem(
    name: String = "Bitcoin",
    symbol: String = "BTC",
    imageIcon: Painter = painterResource(id = R.drawable.bitcoin_icon),
    amount: Double,
    price: Double,
    modifier: Modifier = Modifier,
    walletViewModel: WalletViewModel
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp, bottom = 5.dp)
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
                text = "$amount $symbol",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = walletViewModel.formatCurrency(amount = price),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}

@Composable
fun CryptoAssestsPreview() {
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
private fun AnimatedChart(walletViewModel: WalletViewModel) {

    val animatable = remember {
        Animatable(-90f)
    }

    val finalValue = 270f
    LaunchedEffect(key1 = animatable) {
        animatable.animateTo(
            targetValue = finalValue,
            animationSpec = tween(
                delayMillis = 500,
                durationMillis = 2000
            )
        )
    }

    val currentSweepAngle = animatable.value
    val chartDataList = listOf(
        ChartData(
            MaterialTheme.colorScheme.primaryContainer,
            data = try {
                run {
                    val data = ((walletViewModel.userPortfolio.assets[0].marketPrice / walletViewModel.userPortfolio.balance) * 100).toFloat()
                    println(data)
                    val result = if(walletViewModel.userPortfolio.balance <= 0.0) 200f else data
                    result
                }
            } catch (e: Exception) {
                0f
            },
            crypto = try {
                walletViewModel.userPortfolio.assets[0].name
            } catch (e: Exception) {
                ""
            },
        ),
        ChartData(
            MaterialTheme.colorScheme.onTertiaryContainer,

            data = try {
                run {
                    var data = ((walletViewModel.userPortfolio.assets[1].marketPrice / walletViewModel.userPortfolio.balance) * 100).toFloat()
                    data
                }
            } catch (e: Exception) {
                0f
            },
            crypto = try {
                walletViewModel.userPortfolio.assets[1].name
            } catch (e: Exception) {
                ""
            },
        ),
        ChartData(
            MaterialTheme.colorScheme.onSurfaceVariant,
            data = try {
                run {
                    var data =
                        ((walletViewModel.userPortfolio.assets[2].marketPrice / walletViewModel.userPortfolio.balance) * 100).toFloat()
                    data
                }
            } catch (e: Exception) {
                0f
            },
            crypto = try {
                walletViewModel.userPortfolio.assets[2].name
            } catch (e: Exception) {
                ""
            },
        ),
        ChartData(
            MaterialTheme.colorScheme.error,
            data = if(walletViewModel.otherAssetsTotalSum <= 0.0f && walletViewModel.userPortfolio.balance <= 0.0){
                0f
            }else{
                walletViewModel.otherAssetsTotalSum
            },
            crypto = "Other"
        )
    )


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .offset(y = (-35).dp)
            ) {
                val width = size.width
                val radius = width / 2f
                val strokeWidth = radius * .4f

                var startAngle = -90f

                for (index in 0..chartDataList.lastIndex) {

                    val chartData = chartDataList[index]

                    val sweepAngle = chartData.data.asAngle

                    if (startAngle <= currentSweepAngle) {
                        drawArc(
                            color = chartData.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle.coerceAtMost(currentSweepAngle - startAngle),
                            useCenter = false,
                            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                            size = Size(width - strokeWidth, width - strokeWidth),
                            style = Stroke(strokeWidth)
                        )
                    }

                    startAngle += sweepAngle
                }
            }
        }
        Column(
            modifier = Modifier
                .offset(y = (40).dp)
        ) {
            for (i in chartDataList) {
                DisplayLegend(color = i.color, legend = i.crypto)
            }
        }
    }
}

private val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()

private val Float.asAngle: Float
    get() = this * 360f / 100f

@Immutable
data class ChartData(val color: Color = Color.LightGray, val data: Float, val crypto: String = "")


@Composable
fun DisplayLegend(color: Color, legend: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(color = color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = legend,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
    Spacer(modifier = Modifier.height(6.dp))

}

