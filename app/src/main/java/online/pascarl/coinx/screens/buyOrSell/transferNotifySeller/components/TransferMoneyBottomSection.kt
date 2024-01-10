package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.buyOrSell.BuyCryptoSharedViewModel
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.TransferMoneyViewModel

@Composable
fun TransferMoneyBottomSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel,
    transferMoneyViewModel: TransferMoneyViewModel,
    actionTransferredNotifySeller:() -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                }
        ) {
            Text(
                text = "Help",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = actionTransferredNotifySeller
                )
        ) {
            if (transferMoneyViewModel.transferFundsState.fundsTransferring){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    strokeWidth = 2.dp,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .size(15.dp)
                )
            }else{
                Text(
                    text = "Transferred, notify seller",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

        }
    }
}