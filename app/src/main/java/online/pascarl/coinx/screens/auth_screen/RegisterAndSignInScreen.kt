package online.pascarl.coinx.screens.auth_screen


import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.FetchCryptoPrices
import online.pascarl.coinx.R
import online.pascarl.coinx.authentication.signIn
import online.pascarl.coinx.datasource.expressCheckOut
import online.pascarl.coinx.datasource.userData
import online.pascarl.coinx.datasource.userPortfolio
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.navigation.BottomBarScreen
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.networkcalls.getUserData
import online.pascarl.coinx.networkcalls.getUserPortfolio
import online.pascarl.coinx.rememberImeState


@Composable
fun RegisterScreen(
    navController: NavHostController,
    image:Painter = painterResource(id = R.drawable.coinx)
) {

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var email by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf(false) }
    var showCircularProgressBar by remember{ mutableStateOf(false) }
    val isBlank =  email.isBlank() && password.isBlank()
    val context = LocalContext.current
    val internet = isInternetAvailable(context = context)
    val imeState = rememberImeState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = colorResource(id = R.color.background))


        ) {
            Image(
                painter = image, contentDescription = "logo",
                modifier = Modifier
                    .height(600.dp)
                    .width(600.dp)
            )
            Text(
                text = "Free . Secure . Fast . Trading",
                color = colorResource(id = R.color.app_white),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 130.dp)
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth())
        {

            Text(
                text = "Sign In",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            if (showCircularProgressBar) CircularProgressBar()
            Spacer(modifier = Modifier.height(5.dp))
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)

        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState)
            ){
                /** Email Text Field*/

                Column {
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.body2,

                                )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(onClick = { /*TODO*/ }){
                                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        colors =TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        isError = error,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                /** Password text-field */

                Column {
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.body2

                                )
                        },

                        visualTransformation = if (showPassword) VisualTransformation.None else
                            PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next

                        ),
                        singleLine = true,
                        isError = if (error){
                            true
                        }
                        else if (isBlank){
                            false
                        } else {
                                   false
                        },
                        leadingIcon ={
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon")

                            }
                        },
                        trailingIcon = {
                            if (showPassword){
                                IconButton(onClick = {showPassword = false}) {

                                   Icon(
                                       imageVector = Icons.Filled.Visibility,
                                       contentDescription = "Hide password"
                                   )
                                    //Text(text = "hide")

                                }
                            }else{
                                IconButton(onClick = {showPassword = true}) {

                                   Icon(
                                       imageVector = Icons.Filled.VisibilityOff,
                                       contentDescription = "Hide password"
                                   )
                                    //Text(text = "show")

                                }
                            }
                        },
                        colors =TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()


                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "Reset it here",
                        color = colorResource(id = R.color.background),
                        textDecoration = TextDecoration.Underline,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(Screen.ResetPassword.route)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                /** Sign In Button */
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))

                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                showCircularProgressBar = true
                                showMessage(context, "Signing you in ...")
                                 val signIn = signIn(
                                    email = email,
                                    password = password
                                )
                                val userDataAndPortfolio = try{
                                    userData = getUserData(email = email)
                                    userPortfolio = getUserPortfolio(email = email)
                                }catch (e: Exception){
                                    null
                                }

                                if (signIn == "" && userDataAndPortfolio != null){

                                    showCircularProgressBar = false
                                    navController.popBackStack()
                                    navController.navigate(Screen.BottomBarNavigationContainer.route)
                                }
                                else if(!internet || userDataAndPortfolio == null){
                                    showCircularProgressBar = false
                                    showMessage(context, "No Internet connection")
                                    error = false
                                }
                                else{
                                    showCircularProgressBar = false
                                    showMessage(context, "wrong email or password")
                                    error = true
                                }

                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.background)),



                        ) {
                        Text(
                            text = "Get Started",
                            color = colorResource(id = R.color.app_white),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.W400
                            )
                    }




                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Create Account",
                    color = colorResource(id = R.color.background),
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable {
                            navController.navigate(Screen.CreateAccount.route)
                        }
                )

            }
        }




    }
}

@Composable
fun CircularProgressBar(){
    Card(

        elevation = 10.dp,
            backgroundColor  = Color.White,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(100))

    ){
        CircularProgressIndicator(
            strokeWidth = 3.dp,
            modifier = Modifier
                    .requiredHeight(25.dp)
                    .requiredWidth(25.dp)
            )

    }

}

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}



