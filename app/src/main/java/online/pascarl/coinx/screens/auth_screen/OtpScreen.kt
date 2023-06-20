package online.pascarl.coinx.screens.auth_screen
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen

/*@Preview(showSystemUi = true)
@Composable
fun OtpPreview(){
    OtpScreen()
}*/
@Composable
fun OtpScreen(
    navController: NavHostController,
    resetPasswordViewModel: ResetPasswordViewModel = viewModel(),
){
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        OtpHeader()
        Spacer(modifier = Modifier.height(20.dp))
        OtpTextField(
            otpText = resetPasswordViewModel.otpCode,
            onOtpTextChange ={ value, _ ->
            resetPasswordViewModel.otpCode = value}
        )
        VerifyButton(navController = navController, resetPasswordViewModel = resetPasswordViewModel)
        Spacer(modifier = Modifier.height(10.dp))
      //  ResendOTP(resetPasswordViewModel = resetPasswordViewModel)
    }
}


@Composable
fun OtpHeader(){
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.gray))

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = colorResource(id = R.color.background))


        ) {
            Image(
                painter = painterResource(id = R.drawable.coinx), contentDescription = "logo",
                modifier = Modifier
                    .height(600.dp)
                    .width(600.dp)
                    .align(Alignment.Center)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Aboard",
                    color = colorResource(id = R.color.app_white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 170.dp),
                    textAlign = TextAlign.Center,

                    )
            }

        }


    }
}

@Composable
fun VerifyButton(navController: NavHostController, resetPasswordViewModel: ResetPasswordViewModel){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 30.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(
                if (resetPasswordViewModel.otpCode.length == 6)
                     colorResource(id = R.color.background)
                else Color.LightGray
            )
            .clickable(enabled = resetPasswordViewModel.otpCode.length == 6) {
                scope.launch {
                    if(resetPasswordViewModel.verifyOtp() != null){
                        navController.popBackStack()
                        navController.navigate(Screen.UpdatePasswordScreen.route)
                        showMessage(context, "Verification successful")
                    }
                    else showMessage(context, "Wrong otp code")
                }
            }

    ){
        Text(
            text = "Verify",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .width(40.dp)
                .height(40.dp),
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        )
    }
}
@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(40.dp)
            .height(40.dp)
            .fillMaxSize()
            .border(
                1.dp, when {
                    isFocused -> colorResource(id = R.color.background)
                    else -> Color.LightGray
                }, RoundedCornerShape(8.dp)
            )

    ) {
        Text(
            text = char,
            style = MaterialTheme.typography.h4,
            color = if (isFocused) {
                Color.LightGray
            } else {
                Color.Gray
            },
        )
    }
}

@Composable
fun ResendOTP(resetPasswordViewModel: ResetPasswordViewModel){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = LocalContext.current as? Activity
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        if (resetPasswordViewModel.seconds <= 0){
            Text(
                text = "Resend Code",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W500,
                color = colorResource(id = R.color.cream),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    scope.launch {
                        resetPasswordViewModel.sendOtp(
                            activity = activity!!,
                            phoneNumber = PHONENUMBER
                        )
                        resetPasswordViewModel.startTimer()
                    }
                }
            )
        }else{
            Text(
                text = " Resend in ${resetPasswordViewModel.seconds}",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.cream),
                fontSize = 14.sp,

            )
        }

    }
}
