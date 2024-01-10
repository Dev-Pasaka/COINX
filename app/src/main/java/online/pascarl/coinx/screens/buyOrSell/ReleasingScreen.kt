package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.SELECTEDINDEX
import online.pascarl.coinx.navigation.Screen

@Preview(showSystemUi = true)
@Composable
fun ReleasingPreviewScreen(sharedViewModel: BuyCryptoSharedViewModel = viewModel()){
    val navController = rememberNavController()
    ReleasingScreen(navController = navController, sharedViewModel = sharedViewModel )
}
@Composable
fun ReleasingScreen(navController: NavHostController, sharedViewModel: BuyCryptoSharedViewModel){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        ReleasingTopSection(navController = navController)
        ReleasingMiddleSection(sharedViewModel = sharedViewModel)
        ReleasingBottomSection(navController = navController, sharedViewModel = sharedViewModel)
    }
}

@Composable
fun ReleasingTopSection(navController: NavHostController) {
    Column(
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(360.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                        navController.popBackStack()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)

                )


            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ){
                Text(
                    text = "Releasing",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "95% of this seller's orders " +
                            "have been completed within" +
                            " 12 minutes",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = "11:44",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "mins",
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
@Composable
fun ReleasingMiddleSection(
    sharedViewModel: BuyCryptoSharedViewModel
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = colorResource(id = R.color.background))
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Fiat Amount",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = sharedViewModel.formatCurrency(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Price",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = sharedViewModel.formatCurrency(amount = sharedViewModel.orderData.value.cryptoPrice),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Crypto Amount",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = "${sharedViewModel.youWillGet} ${sharedViewModel.orderData.value.cryptoSymbol}",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}


@Composable
fun ReleasingBottomSection(
    navController: NavHostController,
    sharedViewModel: BuyCryptoSharedViewModel
){
    Column {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ){
            Text(
                text = "Terms",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            Text(
                text = "Strictly terms and conditions to apply Do not mark order as paid before the " +
                        "payment to the seller.",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Read more",
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
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
                    text = "Appeal",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
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
                    ) {
                        navController.navigate(Screen.Orders.route) {
                            SELECTEDINDEX = 3
                            popUpTo(Screen.Orders.route) { inclusive = true }
                        }
                    }
            ) {
                Text(
                    text = "Track order",
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
