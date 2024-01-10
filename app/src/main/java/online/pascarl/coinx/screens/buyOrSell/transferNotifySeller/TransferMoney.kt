package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller

import android.app.Application
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.CryptoAdsScreenViewModel
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components.TransferMoneyBottomSection
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components.TransferMoneyMiddleSection
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components.TransferMoneyTopSection
import online.pascarl.spx.screens.showMessage

@Preview
@Composable
fun TransferPreview() {
    val navController = rememberNavController()
    TransferMoneyScreen(navController = navController)
}

@Composable
fun TransferMoneyScreen(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val transferMoneyViewModel = viewModel<TransferMoneyViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TransferMoneyViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )

    println("Here is order data:${sharedViewModel.orderData}")


    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = scrollState)
    ) {
        TransferMoneyTopSection(
            navController = navController,
            sharedViewModel = sharedViewModel
        )
        TransferMoneyMiddleSection(sharedViewModel = sharedViewModel)
        TransferMoneyBottomSection(
            navController = navController,
            sharedViewModel = sharedViewModel,
            transferMoneyViewModel = transferMoneyViewModel,
            actionTransferredNotifySeller = {
                scope.launch {
                    transferMoneyViewModel.transferredFundsNotifySeller()
                    if (transferMoneyViewModel.transferFundsState.fundsTrasfered){
                        navController.navigate(Screen.Orders.route){
                            navController.popBackStack()
                        }
                    }else{
                        showMessage(context =context, transferMoneyViewModel.transferFundsState.message)
                    }
                }
            }
        )
    }
}





