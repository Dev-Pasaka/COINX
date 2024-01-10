package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.CryptoAdsScreenViewModel


@Composable
fun CryptoSellBodySection(
    cryptoAdsScreenViewModel: CryptoAdsScreenViewModel,
    navController: NavHostController,
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel
) {
    var offsetX by remember { mutableStateOf(Animatable(-200f)) }

    LaunchedEffect(Unit) {
        launch {
            offsetX.animateTo(
                targetValue = 0f, // Adjust this value based on the desired end position
                animationSpec = tween(durationMillis = 500, easing = LinearEasing)
            )
        }
    }

    LazyColumn(
        modifier = Modifier.offset(x = offsetX.value.dp)
    ) {
        if (cryptoAdsScreenViewModel.filteredSellCryptoAds != null){
            items(cryptoAdsScreenViewModel.filteredSellCryptoAds!!.size) {
                CryptoSellAdsItem(
                    sellAd = cryptoAdsScreenViewModel.filteredSellCryptoAds!![it],
                    buyCryptoSharedViewModel = buyCryptoSharedViewModel,
                    navController = navController
                    )
            }
        }
    }


}