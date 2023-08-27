package online.pascarl.coinx.screens.auth_screen

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.rememberImeState

/*@Preview(showSystemUi = true)
@Composable
fun ResetPasswordPreview(){
    ResetPassword()
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(
    navController:NavHostController,
    resetPasswordViewModel: ResetPasswordViewModel = viewModel()){
    val scope  = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()
    val context =  LocalContext.current
    val activity = LocalContext.current as? Activity
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = scrollState)

    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go Back",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(20.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Forgot your password?",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter your registered phone number below \n to receive password reset instructions",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.reset_password_animation))

            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )


            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .height(300.dp)
                    .fillMaxWidth()
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = resetPasswordViewModel.formatedPhoneNumber,
            onValueChange = {
                resetPasswordViewModel.formatedPhoneNumber = it
            },
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone icon") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            isError = !(resetPasswordViewModel.isPhoneVerificationSuccessful == null ||
                    resetPasswordViewModel.isPhoneVerificationSuccessful == true),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                //textColor = MaterialTheme.colorScheme.onSurface,
                //placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            Text(
                text = "Remember password?",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Login",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.W500,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    color = if (resetPasswordViewModel.phoneNumber.length >= 11)
                        MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                )
                .clickable(
                    enabled = resetPasswordViewModel.phoneNumber.length >= 11
                ) {
                    scope.launch {
                        if (resetPasswordViewModel.verifyPhoneNumber()?.status == true) {
                            showMessage(context, "Launching recaptcha ...")
                            resetPasswordViewModel.sendOtp(
                                activity = activity!!,
                                phoneNumber = resetPasswordViewModel.phoneNumber
                            )
                            navController.popBackStack()
                            navController.navigate(Screen.OtpScreen.route)
                            resetPasswordViewModel.startTimer()
                        } else {
                            showMessage(context, "Invalid phone number")
                        }
                    }
                }

        ){
            Text(
                text = "Send",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}

