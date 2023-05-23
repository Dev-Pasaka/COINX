package online.pascarl.coinx.screens.auth_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.*
import online.pascarl.coinx.R
import online.pascarl.coinx.authentication.resetPassword
import online.pascarl.coinx.datasource.UserEmail
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import kotlin.concurrent.timer
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun EmailResetConfirmation(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isCounting by remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = colorResource(id = R.color.app_white))

        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.ResetPassword.route)

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        tint = colorResource(id = R.color.black)
                    )
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Email has been sent!",
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Please check your inbox and click \n the link sent to reset your password",
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
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(

                    painter = imageLoader(imageUri = stringResource(id = R.string.emailConfirmation)),
                    contentDescription = null

                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            color = colorResource(id = R.color.background)
                        )
                        .clickable {
                            navController.navigate(Screen.Register.route)
                        }

                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Didn't receive the link?",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Resend",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.cream),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    modifier = if (!isCounting){
                        Modifier.clickable {
                            scope.launch {
                                val result = resetPassword(UserEmail.email)
                                if (result) {
                                    scaffoldState.snackbarHostState.showSnackbar("Password resent email has been sent")
                                    isCounting = true
                                }else{
                                    scaffoldState.snackbarHostState.showSnackbar("Password resent email not sent")
                                    isCounting = false
                                }

                            }
                        }
                    }else{
                        Modifier
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                var count by remember { mutableStateOf(60) }

                LaunchedEffect(isCounting) {
                    if (isCounting) {
                        withContext(Dispatchers.Default) {
                            for (i in 1..60) {
                                delay(1000)
                                count--
                            }
                        }
                        if (count == 0) {
                            isCounting = false
                            count = 60
                        }
                    }
                }

                Text(
                    text = if (isCounting) "Resend in $count" else "",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.background),
                )

            }


        }


    }
}


suspend  fun counter() :Int{
    var count = 60
    val durationInSeconds = 60

    val time = timer(period = 1000) {
        count--
        println("Count: $count seconds")

    }
    if (count >= durationInSeconds) {
        time.cancel()
        println("Timer complete!")
    }
    return  count
}