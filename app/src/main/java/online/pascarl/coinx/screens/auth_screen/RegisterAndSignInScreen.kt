package online.pascarl.coinx.screens.auth_screen


import android.app.Application
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.rememberImeState
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository


@Composable
fun RegisterScreen(
    navController: NavHostController,
    image:Painter = painterResource(id = R.drawable.coinx),
    signInViewModel: SignInViewModel = viewModel()

) {
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(UserDatabase.getInstance(LocalContext.current.applicationContext).userDao())
    )

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var showPassword by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
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

            if (signInViewModel.circularProgressBar) CircularProgressBar()
            else Text(
                    text = "LogIn",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 5.dp)
                  )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 16.dp)

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
                    OutlinedTextField(
                        value = signInViewModel.email,
                        onValueChange = {
                            signInViewModel.email = it
                        },
                        label = {
                            Text(
                                text = "Email",
                                style = MaterialTheme.typography.body2,

                                )
                        },
                        isError = !signInViewModel.isSignInSuccessful(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = colorResource(id = R.color.background),
                            unfocusedIndicatorColor = colorResource(id = R.color.background),
                            backgroundColor = colorResource(id = R.color.light_gray)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                /** Password text-field */

                Column {
                    OutlinedTextField(
                        value = signInViewModel.password,
                        onValueChange = {
                            signInViewModel.password = it
                        },
                        label = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.body2

                                )
                        },
                        isError = !signInViewModel.isSignInSuccessful(),

                        visualTransformation = if (showPassword) VisualTransformation.None else
                            PasswordVisualTransformation(),
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
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = colorResource(id = R.color.background),
                            unfocusedIndicatorColor = colorResource(id = R.color.background),
                            backgroundColor = colorResource(id = R.color.light_gray)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)


                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Forgot password?",
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
                Spacer(modifier = Modifier.height(16.dp))
                /** Sign In Button */
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20))
                            .background(
                                color = if (signInViewModel.password.isNotBlank() && signInViewModel.email.isNotBlank())
                                    colorResource(id = R.color.background)
                                else colorResource(id = R.color.light_gray)
                            )
                            .clickable(
                                enabled = signInViewModel.password.isNotBlank() && signInViewModel.email.isNotBlank()
                            ) {
                                scope.launch {
                                    //Circular ProgressBard
                                    signInViewModel.circularProgressBar()
                                    //Backend SignIn Auth
                                    signInViewModel.getSignInToken()
                                    if (signInViewModel.isSignInSuccessful()) {
                                        signInViewModel.roomUser = roomDB.getUser(id = "12345678")
                                        if (signInViewModel.roomUser == null) {
                                            roomDB.addUser(
                                                user = RoomUser(
                                                    email = signInViewModel.email,
                                                    token = signInViewModel.backendAuthToken!!
                                                )
                                            )
                                            showMessage(context, "Signing in ...")
                                            navController.navigate(Screen.Dashboard.route)
                                        } else {
                                            roomDB.updateUser(
                                                user = RoomUser(
                                                    email = signInViewModel.email,
                                                    token = signInViewModel.backendAuthToken!!
                                                )
                                            )
                                            showMessage(context, "Signing in ...")
                                            navController.navigate(Screen.Dashboard.route)
                                        }
                                    } else {
                                        showMessage(context, "Invalid email or password")
                                    }
                                }
                            }

                    ) {
                        Text(
                            text = "Login",
                            color = colorResource(id = R.color.app_white),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.W400
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ){
                    Text(
                        text = "Don't have an account? ",
                        style = MaterialTheme.typography.body1,

                    )

                    Text(
                        text = "Sign Up",
                        color = colorResource(id = R.color.cream),
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.CreateAccount.route)
                        }

                    )
                }

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



