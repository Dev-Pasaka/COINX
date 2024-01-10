package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.navigation.Screen

@Composable
fun BuyOrderCreationLowerSection(
    navController: NavHostController,
    actionCancelBuyOrder:() -> Unit,
    actionReadMore:() ->Unit
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8))
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ){
            Text(
                text = "Terms",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            Text(
                text = "Strictly terms and conditions to apply Do not mark order as paid before the " +
                        "payment to the seller.",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Read more",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .clip(shape = CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                        onClick = actionReadMore
                    )
            )
        }

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ){
       Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(color = MaterialTheme.colorScheme.secondary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = actionCancelBuyOrder
                )
        ) {
            Text(
                text = "Cancel",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ) {
                    navController.navigate(Screen.TransferMoneyScreen.route){
                        navController.popBackStack()
                    }
                }
        ) {
            Text(
                text = "Make Payment",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
}