package online.pascarl.coinx.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.auth_screen.showMessage
import online.pascarl.coinx.screens.bottom_bar_navigation.CryptoCoinListItem
import online.pascarl.coinx.screens.bottom_bar_navigation.DashboardViewModel

@Composable
fun SeeAllCryptos(navController: NavHostController, dashboardViewModel: DashboardViewModel = viewModel()){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(color = colorResource(id = R.color.app_white)
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Dashboard.route)

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
                    tint = colorResource(id = R.color.black)
                )
            }

        }

        SeeAllCryptos(dashboardViewModel = dashboardViewModel)

    }
}


@Composable
fun SeeAllCryptos(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel
){

    val context = LocalContext.current

    var isMarketCapSelected by rememberSaveable { mutableStateOf(true) }
    var isPriceSelected by rememberSaveable{ mutableStateOf(false) }
    var is24HrChangeSelected by rememberSaveable{ mutableStateOf(false) }
    val onSelectedBackgroundColor = colorResource(id = R.color.background)
    val onNotSelectedBackgroundColor = Color.Gray

    Row(
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(
            text = "market cap",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (isMarketCapSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    isMarketCapSelected = true
                    isPriceSelected = false
                    is24HrChangeSelected = false

                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "price",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (isPriceSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    isPriceSelected = true
                    isMarketCapSelected = false
                    is24HrChangeSelected = false

                }
        )

        Text(
            text = "24h change",
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = modifier
                .padding(start = 16.dp, top = 8.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(40))
                .background(if (is24HrChangeSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
                .clickable {
                    is24HrChangeSelected = true
                    isPriceSelected = false
                    isMarketCapSelected = false


                }
        )
    }

      LazyColumn(modifier = Modifier.fillMaxWidth()){



    }

}
