package online.pascarl.coinx.screens.bottom_bar_navigation.orders

import android.annotation.SuppressLint
import android.app.Application
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.model.Order
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.NoInternet
import online.pascarl.coinx.screens.bottom_bar_navigation.orders.components.OrderInfo

/*@Preview(showSystemUi = true)
@Composable
fun OrderPreview(){
    Orders()
}*/
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Orders(
    navController: NavHostController,
){
    val context = LocalContext.current
    val ordersViewModel = viewModel<OrdersViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return OrdersViewModel(
                    application = Application(),
                    userRepository = UserRepository(
                        userDao = UserDatabase.getInstance(context).userDao()
                    )
                ) as T
            }
        }
    )


    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ), label = ""
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(1000)
    }

   // val scaffoldState = rememberScaffoldState()

    Scaffold(
      //  scaffoldState = scaffoldState,
        //drawerContent = { NavigationDrawer(navController = navController) },
        bottomBar = { CustomBottomNavigation(
            navController = navController,
        )
        }
    ){
       if (isInternetAvailable(context = context))
           Column(
               modifier = Modifier.alpha(alphaAnim.value)
           ) {
               OrdersHeader()
               FilterOrders(ordersViewModel = ordersViewModel)
               if (ordersViewModel.isOrderLoading){
                   OrdersLoadingPreview()
               }else{
                   Body(ordersViewModel = ordersViewModel)
               }
           }
        else{
            NoInternet()
       }
        if (ordersViewModel.isDialogBoxOpen){
            OrderInfo(
                orderStatus = ordersViewModel.orderStatus,
                order = ordersViewModel.selectedOrder, ordersViewModel =ordersViewModel )
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
            tint = MaterialTheme.colorScheme.onBackground,
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
            tint = MaterialTheme.colorScheme.onBackground,
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
    val onSelectedBackgroundColor = MaterialTheme.colorScheme.primaryContainer
    val onNotSelectedBackgroundColor =  MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    val scope = rememberCoroutineScope()

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(if (isOrderSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
            .clickable {
                scope.launch {
                    ordersViewModel.isOrderSelected(filterOrder = filter)
                }
            }

    ) {
        Text(
            text = filter.item,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
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
           modifier = Modifier
               .background(color = MaterialTheme.colorScheme.background)
               .fillMaxSize()
       ) {
           Text(
               text ="No Orders Present ",
               textAlign = TextAlign.Center,
               color = MaterialTheme.colorScheme.onBackground
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
                   ),
                   ordersViewModel = ordersViewModel
               )
           }
       }

}

@Composable
fun OrderItemBody(order: Order, ordersViewModel: OrdersViewModel){
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
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (order.orderType == "Sell") Color.Red else colorResource(id = R.color.grass_green)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = order.coinSymbol,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    ordersViewModel.orderStatus = order.orderMessage
                    ordersViewModel.selectedOrder = order
                    ordersViewModel.openOrCloseDilogBox()
                }
            ){
                Text(
                    text = order.orderStatus,
                    textAlign = TextAlign.Center,
                    color = when(order.orderStatus){
                        "Expired" -> Color.Red
                        "Pending" -> colorResource(id = R.color.orange)
                        "Completed" -> colorResource(id = R.color.grass_green)
                        else -> Color.Gray},
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription ="Select order",
                    tint = MaterialTheme.colorScheme.onBackground,
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
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
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
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = order.time,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
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
                text = "Order Id ${order.orderId}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "${order.coinSymbol} ${order.orderValue}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
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
                text = "Message ${order.message}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp,),
            color = MaterialTheme.colorScheme.onBackground,
            thickness = 1.dp
        )

    }
}
data class OrderItem(val item:String)

@Composable
fun OrdersLoadingPreview() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fetching))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.background)

    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
        )

    }
}
