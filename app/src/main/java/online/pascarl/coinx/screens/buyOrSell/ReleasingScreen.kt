package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.SELECTEDINDEX
import online.pascarl.coinx.navigation.Screen

@Preview(showSystemUi = true)
@Composable
fun ReleasingPreviewScreen(sharedViewModel: BuyOrSellSharedViewModel = viewModel()){
    val navController = rememberNavController()
    ReleasingScreen(navController = navController, sharedViewModel = sharedViewModel )
}
@Composable
fun ReleasingScreen(navController: NavHostController, sharedViewModel: BuyOrSellSharedViewModel){
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
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
                    tint = Color.Gray,
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
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "95% of this seller's orders " +
                            "have been completed within" +
                            " 12 minutes",
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = "11:44",
                    color = colorResource(id = R.color.orange),
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "mins",
                    color = Color.Gray,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
@Composable
fun ReleasingMiddleSection(
    sharedViewModel: BuyOrSellSharedViewModel
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.background))
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 30.dp, topEnd = 30.dp))
                        .background(
                            color = colorResource(id = R.color.grass_green)
                        )
                ) {
                    Text(
                        text = "Buy",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = imageLoader(symbol = sharedViewModel.orderData.value.cryptoSymbol),
                    contentDescription = "Crypto symbol",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(25.dp)
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
                    text = "Fiat Amount",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Text(
                    text = sharedViewModel.formatCurrency(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 12.sp,
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
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Text(
                    text = sharedViewModel.formatCurrency(amount = sharedViewModel.orderData.value.cryptoPrice),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 12.sp,
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
                    color = Color.White,
                    fontSize = 14.sp,
                )
                Text(
                    text = "${sharedViewModel.youWillGet.value} ${sharedViewModel.orderData.value.cryptoSymbol}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 12.sp,
                )
            }
        }
    }
}


@Composable
fun ReleasingBottomSection(
    navController: NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel
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
                color = Color.DarkGray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            )
            Text(
                text = "Strictly terms and conditions to apply Do not mark order as paid before the " +
                        "payment to the seller.",
                textAlign = TextAlign.Justify,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Read more",
                textAlign = TextAlign.Justify,
                color = colorResource(id = R.color.background),
                fontSize = 14.sp,
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
                    .background(color = colorResource(id = R.color.light_gray))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                    }
            ) {
                Text(
                    text = "Appeal",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(color = colorResource(id = R.color.background))
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
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }
}
