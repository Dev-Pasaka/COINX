package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen

@Preview
@Composable
fun TransferPreview() {
    val navController = rememberNavController()
    TransferMoneyScreen(navController = navController)
}

@Composable
fun TransferMoneyScreen(
    navController: NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(state = scrollState)
    ) {
        TransferMoneyTopSection(
            navController = navController,
            sharedViewModel = sharedViewModel
        )
        TransferMoneyMiddleSection(sharedViewModel = sharedViewModel)
        TransferMoneyBottomSection(
            navController = navController,
            sharedViewModel = sharedViewModel
        )
    }
}


@Composable
fun TransferMoneyTopSection(
    navController: NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel
) {
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
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp))
                    .background(color = colorResource(id = R.color.grass_green))
            ) {
                Text(
                    text = sharedViewModel.orderData.value.adsType,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pay the seller",
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 2.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "Order will be cancelled in ",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
                Text(
                    text = "13.15",
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.orange),
                    fontSize = 12.sp,

                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = sharedViewModel.formatCurrency(amount = sharedViewModel.youWillPay.value),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.background),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)
                        .clip(RoundedCornerShape(360.dp))

                )

            }

        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8))
                .background(color = colorResource(id = R.color.light_orange))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            shape = CircleShape,
                            color = colorResource(id = R.color.orange)
                        )
                ) {
                    Text(
                        text = "!",
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "For security reasons, only use your own account to transfer funds to the" +
                            "seller. Payments made from accounts not matching your verified KYC name" +
                            " will not be accepted.",
                    textAlign = TextAlign.Justify,
                    color = colorResource(id = R.color.orange),
                    fontSize = 12.sp,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun TransferMoneyMiddleSection(sharedViewModel: BuyOrSellSharedViewModel) {
    Column(
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.background)),

            ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                ) {
                    Text(
                        text = "Leave the coinx app, open your selected banking or payment platform with an" +
                                "account name that matches your verified name on coinx, and transfer the" +
                                "funds to the seller's account provided below.",
                        textAlign = TextAlign.Justify,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp, top = 8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.light_gray)),
                ) {
                   Column(
                       verticalArrangement = Arrangement.SpaceBetween
                   ) {
                       Column(
                           horizontalAlignment = Alignment.Start,
                       ) {
                           Text(
                               text = "M-pesa Paybill",
                               textAlign = TextAlign.Justify,
                               color = colorResource(id = R.color.background),
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .padding(horizontal = 16.dp, vertical = 4.dp)
                           )
                       }
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.SpaceBetween,
                           modifier = Modifier.fillMaxWidth(),
                       ) {
                           Text(
                               text = "Business Name",
                               textAlign = TextAlign.Justify,
                               color = Color.Gray,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .padding(horizontal = 16.dp, vertical = 4.dp)
                           )
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.Start,
                               modifier = Modifier.padding(end = 16.dp),
                           ) {
                               Text(
                                   text = "IM bank",
                                   textAlign = TextAlign.Justify,
                                   color = Color.Gray,
                                   fontSize = 12.sp,
                                   modifier = Modifier
                               )
                               Spacer(modifier = Modifier.width(5.dp))
                               Icon(
                                   imageVector = Icons.Default.ContentCopy,
                                   contentDescription = "Copy",
                                   tint = Color.Gray,
                                   modifier = Modifier
                                       .size(15.dp)
                                       .clip(RoundedCornerShape(360.dp))

                               )
                           }
                       }
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.SpaceBetween,
                           modifier = Modifier.fillMaxWidth(),
                       ) {
                           Text(
                               text = "Business Number",
                               textAlign = TextAlign.Justify,
                               color = Color.Gray,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .padding(horizontal = 16.dp, vertical = 4.dp)
                           )
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.Start,
                               modifier = Modifier.padding(end = 16.dp),
                           ) {
                               Text(
                                   text = "001301",
                                   textAlign = TextAlign.Justify,
                                   color = Color.Gray,
                                   fontSize = 12.sp,
                                   modifier = Modifier
                               )
                               Spacer(modifier = Modifier.width(5.dp))
                               Icon(
                                   imageVector = Icons.Default.ContentCopy,
                                   contentDescription = "Copy",
                                   tint = Color.Gray,
                                   modifier = Modifier
                                       .size(15.dp)
                                       .clip(RoundedCornerShape(360.dp))

                               )
                           }
                       }
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.SpaceBetween,
                           modifier = Modifier.fillMaxWidth(),
                       ) {
                           Text(
                               text = "Account Number",
                               textAlign = TextAlign.Justify,
                               color = Color.Gray,
                               fontSize = 12.sp,
                               modifier = Modifier
                                   .padding(horizontal = 16.dp, vertical = 4.dp)
                           )
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.Start,
                               modifier = Modifier.padding(end = 16.dp),
                           ) {
                               Text(
                                   text = "00134543601",
                                   textAlign = TextAlign.Justify,
                                   color = Color.Gray,
                                   fontSize = 12.sp,
                                   modifier = Modifier
                               )
                               Spacer(modifier = Modifier.width(5.dp))
                               Icon(
                                   imageVector = Icons.Default.ContentCopy,
                                   contentDescription = "Copy",
                                   tint = Color.Gray,
                                   modifier = Modifier
                                       .size(15.dp)
                                       .clip(RoundedCornerShape(360.dp))

                               )
                           }
                       }
                   }

                }

            }

        }
    }
}

@Composable
fun TransferMoneyBottomSection(
    navController: NavHostController,
    sharedViewModel: BuyOrSellSharedViewModel
){
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
                text = "Help",
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
                    navController.navigate(Screen.ReleasingScreen.route)
                }
        ) {
            Text(
                text = "Transferred, notify seller",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
}
