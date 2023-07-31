package online.pascarl.coinx.nav_drawer

import android.app.Application
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.auth_screen.showMessage

@Preview(showSystemUi = true)
@Composable
fun MerchantInformationPreview(){
    MerchantPaymentInformation()
}

@Composable
fun MerchantPaymentInformation(navController:NavHostController = rememberNavController()){
    val context = LocalContext.current.applicationContext
    val merchantInformationViewModel = viewModel<MerchantPaymentInformationViewModel>(
        factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MerchantPaymentInformationViewModel(
                    application = Application(),
                    userRepository = UserRepository(userDao =UserDatabase.getInstance(context).userDao() )
                ) as T
            }
        }
    )
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Home",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true),
                    ) {
                        navController.navigate(Screen.Dashboard.route){
                            popUpTo(Screen.Dashboard.route){inclusive = true}
                        }
                    }
                )
            Text(
                text = "Coinx",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = "",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge,

                )
        }
        Text(
            text = "Add Payment Options",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ){
            items(count = merchantInformationViewModel.paymentMethods.size){
                PaymentItem(
                    paymentMethod = merchantInformationViewModel.paymentMethods[it],
                    isSelected = merchantInformationViewModel.paymentMethods[it] ==
                    merchantInformationViewModel.selectedPaymentMethod,
                    merchantInformationViewModel = merchantInformationViewModel
                )
            }
        }
        when(merchantInformationViewModel.selectedPaymentMethod){
            "Mpesa Safaricom" -> MpesaSafaricomPaymentMethod(merchantInformationViewModel = merchantInformationViewModel)
            "Mpesa Paybill" -> MpesaPaybillPaymentMethod(merchantInformationViewModel = merchantInformationViewModel)
            "Mpesa Till" -> MpesaTillPaymentMethod(merchantInformationViewModel = merchantInformationViewModel)
        }
    }
}

@Composable
fun PaymentItem(
    paymentMethod:String = "Mpesa Safaricom",
    isSelected:Boolean,
    merchantInformationViewModel: MerchantPaymentInformationViewModel
){
    val onSelectedBackgroundColor = MaterialTheme.colorScheme.primaryContainer
    val onNotSelectedBackgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20))
            .background(if (isSelected) onSelectedBackgroundColor else onNotSelectedBackgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
            ) {
                merchantInformationViewModel.changePaymentMethod(paymentMethod)
            }

    ) {
        Text(
            text = paymentMethod,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(10.dp)

        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpesaSafaricomPaymentMethod(merchantInformationViewModel: MerchantPaymentInformationViewModel){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Mpesa Safaricom",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier

            )

        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Phone number",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(10.dp)

            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                value = merchantInformationViewModel.mpesaSafaricomNumberInput,
                onValueChange = {
                    merchantInformationViewModel.mpesaSafaricomNumberInput = it
                },
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone icon") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = merchantInformationViewModel.mpesaSafaricomNumber?.length!! >= 10,
                    onClick = {
                        scope.launch {
                            merchantInformationViewModel.addMpesaSafaricom()
                            if (merchantInformationViewModel.isBecomeMerchantSuccess) showMessage(
                                context = context,
                                "Payment method added successfully"
                            )
                            else showMessage(
                                context = context,
                                "Payment method failed"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Add Payment",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpesaPaybillPaymentMethod(merchantInformationViewModel: MerchantPaymentInformationViewModel){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Mpesa Safaricom",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier

            )

        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Business Number",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = merchantInformationViewModel.bussinessNumber?:"",
                onValueChange = {
                    merchantInformationViewModel.bussinessNumber = it
                },
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Filled.Business, contentDescription = "Business number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()

            )
            Text(
                text = "Account Number",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = merchantInformationViewModel.accountNumber?:"",
                onValueChange = {
                    merchantInformationViewModel.accountNumber = it
                },
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Business number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = merchantInformationViewModel.bussinessNumber?.isNotBlank() == true
                            && merchantInformationViewModel.accountNumber?.isNotBlank() == true,
                    onClick = {
                        scope.launch {
                            merchantInformationViewModel.addMpesaPaybill()
                            if (merchantInformationViewModel.isBecomeMerchantSuccess) showMessage(
                                context = context,
                                "Payment method added successfully"
                            )
                            else showMessage(
                                context = context,
                                "Payment method failed"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Add Payment",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MpesaTillPaymentMethod(merchantInformationViewModel: MerchantPaymentInformationViewModel){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Mpesa Safaricom",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier

            )

        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Till Number",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = merchantInformationViewModel.tillNumber?:"",
                onValueChange = {
                    merchantInformationViewModel.tillNumber = it
                },
                singleLine = true,
                leadingIcon = { Icon(imageVector = Icons.Filled.CreditCard, contentDescription = "Till number") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = merchantInformationViewModel.tillNumber?.isNotBlank() == true
                            && merchantInformationViewModel.tillNumber?.length!! >= 4,
                    onClick = {
                        scope.launch {
                            merchantInformationViewModel.addMpesaTill()
                            if (merchantInformationViewModel.isBecomeMerchantSuccess) showMessage(
                                context = context,
                                "Payment method added successfully"
                            )
                            else showMessage(
                                context = context,
                                "Payment method failed"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Add Payment",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }

    }
}