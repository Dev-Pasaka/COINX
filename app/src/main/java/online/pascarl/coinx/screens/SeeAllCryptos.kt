package online.pascarl.coinx.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.screens.bottom_bar_navigation.DashboardViewModel



@Composable
fun SeeAllCryptos(
    navController:NavHostController,
    seeAllCryptosViewModel: SeeAllCryptosViewModel = viewModel()
){
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
    Column(
        modifier = Modifier.alpha(alphaAnim.value
        )
    ) {
        SeeAllCryptosHeader(navController = navController)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            LazyRow{
                items(count = seeAllCryptosViewModel.sortItems.size){
                    SortCryptosItem(
                        sortBy = seeAllCryptosViewModel.sortItems[it],
                        isSortMethodSelected = seeAllCryptosViewModel.sortItems[it]
                                == seeAllCryptosViewModel.filterSelected,
                        seeAllCryptosViewModel = seeAllCryptosViewModel
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            if (seeAllCryptosViewModel.cryptocurrencies.isNotEmpty()){
                LazyColumn{
                    items(count =seeAllCryptosViewModel.cryptocurrencies.size ){
                        SortCryptoListItem(
                            name = seeAllCryptosViewModel.cryptocurrencies[it]?.name ?: "",
                            symbol = seeAllCryptosViewModel.cryptocurrencies[it]?.symbol ?: "",
                            imageIcon = imageLoader(symbol = seeAllCryptosViewModel.cryptocurrencies[it]?.symbol ?: "BTC"),
                            percentageChange = seeAllCryptosViewModel.cryptocurrencies[it]?.percentageChange24h?.toDouble() ?: 0.0,
                            price = seeAllCryptosViewModel.formatCurrency(
                                amount = seeAllCryptosViewModel.cryptocurrencies[it]?.price?.toDouble() ?: 0.0
                            ),
                            seeAllCryptosViewModel = seeAllCryptosViewModel
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            }else{
                SeeAllCryptosLoadingPreview()
            }
        }
    }

}

@Composable
fun SeeAllCryptosHeader(navController: NavHostController){
   Row(
       horizontalArrangement = Arrangement.SpaceBetween,
       verticalAlignment = Alignment.CenterVertically,
       modifier = Modifier
           .fillMaxWidth()
           .padding(16.dp)
   ){
       Icon(
           imageVector = Icons.Default.ArrowBackIos,
           contentDescription = "Go Back",
           tint = Color.Gray,
           modifier = Modifier
               .size(20.dp)
               .clip(RoundedCornerShape(360.dp))
               .clickable {
                   navController.popBackStack()
               }
       )

       Row(
           horizontalArrangement = Arrangement.End,
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 16.dp, end = 16.dp)
       ) {

           Icon(
               painter = painterResource(id = R.drawable.qr_code_scanner),
               contentDescription = "Code Scanner",
               tint = colorResource(id = R.color.background),
               modifier = Modifier
                   .size(25.dp)
                   .clip(RoundedCornerShape(360.dp))
                   .clickable {

                   }
           )
           Spacer(modifier = Modifier.width(16.dp))

           Icon(
               imageVector = Icons.Outlined.Notifications,
               contentDescription = "Notifications",
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
fun SortCryptosItem(
    sortBy:String,
    isSortMethodSelected:Boolean,
    seeAllCryptosViewModel: SeeAllCryptosViewModel
){
    val onSelectedBackgroundColor = colorResource(id = R.color.background)
    val onNotSelectedBackgroundColor = Color.LightGray

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(if (isSortMethodSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
            .clickable {
                seeAllCryptosViewModel.sort(sortBy = sortBy)
            }

    ) {
        Text(
            text = sortBy,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = Modifier
                .padding(10.dp)

        )

    }
}


@Composable
fun SortCryptoListItem(
    name: String?,
    symbol: String?,
    imageIcon: Painter?,
    percentageChange: Double?,
    price: String?,
    seeAllCryptosViewModel: SeeAllCryptosViewModel
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imageIcon!!,
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
                    text = name!!,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black),
                )
                Text(
                    text = symbol!!,
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
                text = "$percentageChange%",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = if (percentageChange!! < 0.0)
                    colorResource(id = R.color.red) else colorResource(id = R.color.grass_green)
            )
            Text(
                text = price!!,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = colorResource(id = R.color.black),
            )
        }
    }

}


@Composable
fun SeeAllCryptosLoadingPreview() {
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

