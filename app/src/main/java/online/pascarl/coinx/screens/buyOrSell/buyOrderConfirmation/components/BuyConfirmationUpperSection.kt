package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.BuyConfirmationScreenViewModel

@Composable
fun BuyConfirmationUpperSection(
    navController: NavHostController,
    buyCryptoSharedViewModel: BuyCryptoSharedViewModel
){
    val  buyConfirmationScreenViewModel = viewModel<BuyConfirmationScreenViewModel>()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(360.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    navController.popBackStack()
                }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(2.dp)
                    .size(20.dp)
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text =  "You will pay",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buyConfirmationScreenViewModel.formatCurrency(amount = buyCryptoSharedViewModel.youWillPay),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}