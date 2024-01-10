package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation

import android.app.Application
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.CryptoAdsScreenViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components.BuyConfirmationLowerSection
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components.BuyConfirmationMiddleSection
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components.BuyConfirmationUpperSection
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model.CreateBuyOrder
import online.pascarl.spx.screens.showMessage

@Composable
fun BuyConfirmationScreen(
    navController:NavHostController = rememberNavController(),
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val buyConfirmationScreenViewModel = viewModel<BuyConfirmationScreenViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BuyConfirmationScreenViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )
    buyConfirmationScreenViewModel.buyOrder = CreateBuyOrder(
        adId = buyCryptoSharedViewModel.buyAdData?.id,
        cryptoName = buyCryptoSharedViewModel.buyAdData?.cryptoName,
        cryptoSymbol =  buyCryptoSharedViewModel.buyAdData?.cryptoSymbol,
        cryptoAmount = buyCryptoSharedViewModel.youWillGet,

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
            .padding(vertical = 16.dp)
            .fillMaxSize()
            .alpha(alphaAnim.value)
    ) {
        BuyConfirmationUpperSection(
            navController = navController,
            buyCryptoSharedViewModel = buyCryptoSharedViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        BuyConfirmationMiddleSection(
            buyConfirmationScreenViewModel = buyConfirmationScreenViewModel,
            sharedViewModel = buyCryptoSharedViewModel
        )
        Spacer(modifier = Modifier.height(24.dp))
        BuyConfirmationLowerSection(
            navController = navController,
            sharedViewModel = buyCryptoSharedViewModel,
            buyConfirmationScreenViewModel = buyConfirmationScreenViewModel,
            actionConfirm = {
                scope.launch{
                    buyConfirmationScreenViewModel.createBuyOrder()
                    if (buyConfirmationScreenViewModel.orderStatus.orderCreated){
                        navController.navigate(Screen.BuyOrderCreationScreen.route){
                            popUpTo(route = Screen.BuyConfirmationScreen.route){inclusive = true}
                        }
                        showMessage(context = context, message = "Order was created successfully")
                    }else{
                        showMessage(context = context, message = "Failed to create order")
                    }
                }

            }
        )


    }
}


