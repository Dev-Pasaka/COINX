package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.screens.buyOrSell.enterBuyAmount.BuyAmountViewModel


@Composable
fun BuyNumberPad(
    navController: NavHostController,
    buyAmountViewModel: BuyAmountViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "1", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "2", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "3", buyAmountViewModel = buyAmountViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "4", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "5", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "6", buyAmountViewModel = buyAmountViewModel)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = "7", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "8", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "9", buyAmountViewModel = buyAmountViewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            BuyNumberKey(numberKey = ".", buyAmountViewModel = buyAmountViewModel)
            BuyNumberKey(numberKey = "0", buyAmountViewModel = buyAmountViewModel)
            Column(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        buyAmountViewModel.cryptoBuyAmount =
                            buyAmountViewModel.removeLastCharacter(
                                input = buyAmountViewModel.cryptoBuyAmount
                            )
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Backspace,
                    contentDescription = "Back Space",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

        }

    }
}


