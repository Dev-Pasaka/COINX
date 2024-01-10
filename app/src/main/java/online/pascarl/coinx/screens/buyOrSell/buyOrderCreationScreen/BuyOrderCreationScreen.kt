package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen

import android.app.Application
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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.model.OrderCreatedModel
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.BuyConfirmationScreenViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components.BuyOrderCreationLowerSection
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components.BuyOrderCreationMiddleSection
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components.BuyOrderCreationTopSection
import online.pascarl.spx.screens.showMessage




@Composable
fun BuyOrderCreationScreen(
    navController:NavHostController,
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel
) {
    val context = LocalContext.current
    val buyOrderCreationViewModel = viewModel<BuyOrderCreationViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BuyOrderCreationViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )

    buyOrderCreationViewModel.adId = buyCryptoSharedViewModel.buyAdData?.id ?: ""

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
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
            .verticalScroll(state = scrollState)
            .alpha(alphaAnim.value)
    ) {
        BuyOrderCreationTopSection(navController = navController,  sharedViewModel = buyCryptoSharedViewModel)
        BuyOrderCreationMiddleSection(navController = navController)
        BuyOrderCreationLowerSection(
            navController = navController,
            actionCancelBuyOrder = {
                scope.launch {
                    buyOrderCreationViewModel.cancelBuyOrder()
                    if (buyOrderCreationViewModel.isOrderCancelled){
                        navController.navigate(
                            route = Screen.BuyOrSellCryptos.route
                        ) {
                            popUpTo(Screen.Dashboard.route) { inclusive = false }
                        }
                        showMessage(context = context, message = "Order was cancelled successfully")
                    }else{
                        showMessage(context = context, message = "Failed to cancel order")
                    }
                }

            },
            actionReadMore = {}
        )
    }

}