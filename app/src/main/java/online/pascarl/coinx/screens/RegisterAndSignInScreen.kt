package online.pascarl.coinx.screens


import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.authentication.signIn
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.showMessage


@Composable
fun RegisterScreen(
    navController: NavHostController,
    image:Painter = painterResource(id = R.drawable.coinx
    )) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var email by remember{
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf(false) }
    val isBlank =  email.isBlank() && password.isBlank()
    val context = LocalContext.current


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
                modifier = Modifier.padding(top = 150.dp)
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
            
        }      

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
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
                Spacer(modifier = Modifier.height(16.dp))
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

                            }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
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
                                showMessage(context, "Signing you in ...")
                                 val signIn = signIn(
                                    email = email,
                                    password = password
                                )
                                if (signIn == ""){
                                    navController.popBackStack()
                                    navController.navigate(Screen.Dashboard.route)
                                }else{
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

                Spacer(modifier = Modifier.height(250.dp))
            }
        }




    }
}

@Composable
fun GoogleButton(
    image:Painter = painterResource(id = R.drawable.google),
    modifier: Modifier = Modifier
){

    val scope = rememberCoroutineScope()
    val context = LocalContext.current as ComponentActivity
    val signInLauncher = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task: SignInCredential = Identity.getSignInClient(context).getSignInCredentialFromIntent(result.data)
        try {
            val idToken = task.googleIdToken
            // TODO: Use the id token to authenticate the user with your backend
        } catch (e: ApiException) {
            // Handle error
        }
    }

    IconButton(
        modifier = modifier
            .width(70.dp)
            .height(55.dp)
            .padding(start = 16.dp)
            .clip(shape = CircleShape),
        onClick = {

            scope.launch {
                val request = BeginSignInRequest.builder()
                    .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                            .setServerClientId("241143851123-2psprgvd68oq4r2uh7lr9hqbj2cmrvpr.apps.googleusercontent.com")
                            .build()
                    )
                    .build()

            }

        }
    ) {
        Image(
            painter = image,
            contentDescription = "Google Icon",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)

        )

    }
}

@Composable
fun TwitterButton(
    image:Painter = painterResource(id = R.drawable.google),
    modifier: Modifier
){


    IconButton(
        modifier = modifier
            .width(70.dp)
            .height(55.dp)
            .padding(start = 16.dp)
            .clip(shape = CircleShape),
        onClick = {
        }
    ) {
        Image(
            painter = image,
            contentDescription = "Google Icon",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)

        )

    }
}



@Composable
fun GitHubButton(
    image:Painter = painterResource(id = R.drawable.google),
    modifier: Modifier
){


    IconButton(
        modifier = modifier
            .width(70.dp)
            .height(55.dp)
            .padding(start = 16.dp)
            .clip(shape = CircleShape),
        onClick = {

        }
    ) {
        Image(
            painter = image,
            contentDescription = "Google Icon",
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)

        )

    }
}

@Composable
fun ErrorField(error: String){
    Text(
        text =error,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(color = MaterialTheme.colors.error)
    )

}

@Composable
fun Button(onClick:(String)->Unit){
    }

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


