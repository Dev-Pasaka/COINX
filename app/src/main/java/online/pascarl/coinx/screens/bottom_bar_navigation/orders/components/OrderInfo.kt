package online.pascarl.coinx.screens.bottom_bar_navigation.orders.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.model.Order
import online.pascarl.coinx.screens.bottom_bar_navigation.orders.OrdersViewModel

@Composable
fun OrderInfo(orderStatus: String, order: Order, ordersViewModel: OrdersViewModel) {
    val scope = rememberCoroutineScope()
    Dialog(
        onDismissRequest = { ordersViewModel.openOrCloseDilogBox() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        ElevatedCard(
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
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
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = order.orderStatus,
                            textAlign = TextAlign.Center,
                            color = when (order.orderStatus) {
                                "Expired" -> Color.Red
                                "Pending" -> colorResource(id = R.color.orange)
                                "Completed" -> colorResource(id = R.color.grass_green)
                                else -> Color.Gray
                            },
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }


                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
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
                        .padding(vertical = 2.dp)

                ) {
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
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "Order Id ${order.orderId}",
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
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "${order.coinSymbol} ${order.orderValue}",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }


                if (orderStatus == "MERCHANT_HAS_TRANSFERRED_FUNDS") {

                    if (ordersViewModel.fundsTransferering) {

                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            strokeWidth = 2.dp,
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .size(15.dp)
                        )

                    } else{
                        Button(
                            onClick = { /*TODO*/ },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Release Crypto",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }

                } else if (orderStatus == "BUYER_HAS_TRANSFERRED_FUNDS") {
                    if (ordersViewModel.fundsTransferering) {

                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            strokeWidth = 2.dp,
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .size(15.dp)
                        )

                    } else {
                        Button(
                            onClick = {
                                scope.launch {
                                    ordersViewModel.transferredFundsNotifySeller(order.orderType)
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Confirm Funds Transferred",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }

                }

            }

        }
    }
}



