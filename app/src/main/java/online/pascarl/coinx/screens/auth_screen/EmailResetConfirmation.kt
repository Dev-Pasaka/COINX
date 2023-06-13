package online.pascarl.coinx.screens.auth_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun EmailResetConfirmation(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
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
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Password reset is successful!",
                    style = MaterialTheme.typography.h4,
                    color = colorResource(id = R.color.background),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You can now login with your new password",
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
                            navController.popBackStack()
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
            Spacer(modifier = Modifier.height(32.dp))



        }
    }


}