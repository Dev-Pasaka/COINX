package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds

import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoAdsHeader
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoAdsScreenLoading
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoAdsSubHeader
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoBuyBodySection
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoBuyOrSellToggle
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components.CryptoSellBodySection
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.BuyOrSellState


@Composable
fun CryptoAdsScreen(
    navController: NavHostController,
    buyCryptoSharedViewModel:BuyCryptoSharedViewModel = viewModel()
) {

    val context = LocalContext.current
    val cryptoAdsScreenViewModel = viewModel<CryptoAdsScreenViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CryptoAdsScreenViewModel(
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
            .alpha(alphaAnim.value)
    ) { 
        CryptoAdsHeader(navController = navController)
        CryptoBuyOrSellToggle(cryptoAdsScreenViewModel = cryptoAdsScreenViewModel)
        CryptoAdsSubHeader()
        when(cryptoAdsScreenViewModel.isBuySelected){
            BuyOrSellState.SELL -> {
                if(cryptoAdsScreenViewModel.filteredSellCryptoAds== null){
                    CryptoAdsScreenLoading()
                }else{
                    CryptoSellBodySection(
                        cryptoAdsScreenViewModel = cryptoAdsScreenViewModel,
                        buyCryptoSharedViewModel = buyCryptoSharedViewModel,
                        navController = navController
                        )

                }
            }
            BuyOrSellState.BUY ->{
                if(cryptoAdsScreenViewModel.filteredBuyCryptoAds== null){
                    CryptoAdsScreenLoading()
                }else{
                    CryptoBuyBodySection(
                        cryptoAdsScreenViewModel = cryptoAdsScreenViewModel,
                        buyCryptoSharedViewModel = buyCryptoSharedViewModel,
                        navController = navController
                    )
                }
            }
        }

    }


}










