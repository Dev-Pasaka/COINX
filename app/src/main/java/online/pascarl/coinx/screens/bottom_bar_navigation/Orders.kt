package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import online.pascarl.coinx.R
import online.pascarl.coinx.model.Order
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.NavigationDrawer

/*@Preview(showSystemUi = true)
@Composable
fun OrderPreview(){
    Orders()
}*/
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Orders(
    navController: NavHostController,
    bottomBarViewModel: BottomBarViewModel = viewModel(),
    ordersViewModel: OrdersViewModel  = viewModel()
){
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { NavigationDrawer(navController = navController) },
        bottomBar = { CustomBottomNavigation(
            navController = navController,
            bottomBarViewModel = bottomBarViewModel
        )
        }
    ){
        Column(
            modifier = Modifier.alpha(alphaAnim.value)
        ) {
            OrdersHeader()
            FilterOrders(ordersViewModel = ordersViewModel)
            Body(ordersViewModel = ordersViewModel)
        }
    }

}

@Composable
fun OrdersHeader(){
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, end = 16.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.qr_code_scanner),
            contentDescription = "Code Scanner",
            tint = colorResource(id = R.color.background),
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(360.dp))
                .clickable {

                }
        )
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            tint = colorResource(id = R.color.background),
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(360.dp))
                .clickable {

                },

            )
    }
}
@Composable
fun FilterOrders(ordersViewModel: OrdersViewModel){
    Row(modifier = Modifier.padding(16.dp)) {
        LazyRow{
            items(ordersViewModel.orderItemFilterList.size){
                FilterOrdersItem(
                    filter = ordersViewModel.orderItemFilterList[it],
                    ordersViewModel = ordersViewModel,
                    isOrderSelected = ordersViewModel.orderSelected == ordersViewModel.orderItemFilterList[it].item
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun FilterOrdersItem(
    filter: OrderItem,
    ordersViewModel: OrdersViewModel,
    isOrderSelected:Boolean
){
    val onSelectedBackgroundColor = colorResource(id = R.color.background)
    val onNotSelectedBackgroundColor =  Color.LightGray

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(if (isOrderSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
            .clickable {
                ordersViewModel.isOrderSelected(filterOrder = filter)
            }

    ) {
        Text(
            text = filter.item,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = colorResource(id = R.color.app_white),
            modifier = Modifier
                .padding(10.dp)

        )

    }
}

@Composable
fun Body(ordersViewModel: OrdersViewModel){
    val orders = ordersViewModel.filteredList
   if (orders.isEmpty()){
       Column(
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.fillMaxSize()
       ) {
           Text(
               text ="No Orders Present ",
               style = MaterialTheme.typography.body1,
               textAlign = TextAlign.Center,
               color = Color.Gray
           )

       }
   }else
       LazyColumn{
           items(count = orders.size){
               OrderItemBody(
                   order = Order(
                       orderType = orders[it].orderType,
                       coinSymbol = orders[it].coinSymbol,
                       price = orders[it].price,
                       amount = orders[it].amount,
                       orderId = orders[it].orderId,
                       orderStatus = orders[it].orderStatus,
                       orderValue = orders[it].orderValue,
                       time = orders[it].time
                   )
               )
           }
       }

}

@Composable
fun OrderItemBody(order: Order){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = order.orderType,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = if (order.orderType == "Sell") Color.Red else colorResource(id = R.color.grass_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = order.coinSymbol,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = order.orderStatus,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    color = when(order.orderStatus){
                        "Expired" -> Color.Red
                        "Pending" -> colorResource(id = R.color.bamboo)
                        "Completed" -> colorResource(id = R.color.grass_green)
                        else -> Color.Gray},
                    fontSize = 14.sp,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription ="Select order",
                    modifier = Modifier.size(10.dp)
                )
            }


        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ){
            Text(
                text = "Price Ksh ${order.price}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ){
            Text(
                text = "Amount ${order.amount}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = order.time,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ){
            Text(
                text = "Order ${order.orderId}",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = order.orderValue,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = colorResource(id = R.color.background)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp,),
            color = Color.LightGray,
            thickness = 1.dp
        )

    }
}
data class OrderItem(val item:String)
