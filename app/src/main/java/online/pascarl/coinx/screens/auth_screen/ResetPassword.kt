package online.pascarl.coinx.screens.auth_screen

import android.app.Activity
import android.app.Application
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import io.ktor.http.ContentType
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.rememberImeState

/*@Preview(showSystemUi = true)
@Composable
fun ResetPasswordPreview(){
    ResetPassword()
}*/

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
            .background(color = colorResource(id = R.color.app_white))
            .verticalScroll(state = scrollState)

    ) {
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
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter your registered phone number below \n to receive password reset instructions",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 16.sp
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
            label = {
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.body2,
                    )
            },
            textStyle =  LocalTextStyle.current.copy(color = Color.Gray),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Email icon") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            isError = !(resetPasswordViewModel.isPhoneVerificationSuccessful == null ||
                    resetPasswordViewModel.isPhoneVerificationSuccessful == true),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = colorResource(id = R.color.background)
            ),
            shape = RoundedCornerShape(10.dp),
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
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Login",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.cream),
                fontSize = 14.sp,
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
                    color = if (resetPasswordViewModel.phoneNumber.isBlank())
                        Color.LightGray else colorResource(id = R.color.background)
                )
                .clickable(
                    enabled = resetPasswordViewModel.phoneNumber.isNotBlank()
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
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp
            )
        }

    }
}

