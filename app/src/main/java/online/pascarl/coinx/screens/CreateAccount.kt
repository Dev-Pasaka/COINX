package online.pascarl.spx.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.authentication.createAccount
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.model.User
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.networkcalls.registerUser
import online.pascarl.coinx.networkcalls.validateUserCreationResponse

@Composable
fun CreateAccount(
    image: Painter = painterResource(id = R.drawable.coinx),
    navController: NavHostController
){
    var fullName by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var registerPassword by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val internet = isInternetAvailable(context = context)


    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.gray))

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = colorResource(id = R.color.background))


        ) {
            Text(
                text = "cancel",
                color = colorResource(id = R.color.gray),
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 30.dp, start = 16.dp)
                    .clickable {

                        navController.popBackStack()

                    }

            )
            Image(
                painter = image, contentDescription = "logo",
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
                            modifier = Modifier.padding(top = 190.dp),
                    textAlign = TextAlign.Center,

                    )
            }

        }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                fontSize = 16.sp,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(top = 5.dp),
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(state = scrollState)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 20.dp)




                    // .weight(weight =1f, fill = false)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    /** Full Name text-field */
                    TextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = {
                            Text(
                                text = "Full Name",
                                style = MaterialTheme.typography.body2

                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.AccountBox,
                                    contentDescription = "Create account"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()

                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    /** Username text-field */

                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = {
                            Text(
                                text = "Username",
                                style = MaterialTheme.typography.body2

                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Create account"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()

                    )
                    
                    if (userName.length in 2..2){
                        //ErrorComposable(message = "Username must be more than 3 charters")
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    /** Email text-field */

                    Column {
                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            label = {
                                Text(
                                    text = "Email",
                                    style = MaterialTheme.typography.body2

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
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    if (!email.contains("@")){
                        //ErrorComposable(message = "Use a valid email address")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    /** Password text-field */

                    // Register password
                    var showPassword by rememberSaveable { mutableStateOf(false) }
                    Column {
                        TextField(
                            value = registerPassword,
                            onValueChange = {
                                registerPassword = it
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

                            leadingIcon ={
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon")

                                }
                            },
                            trailingIcon = {
                                if (showPassword){
                                    IconButton(onClick = {showPassword = false}) {
                                        Text(
                                            text = "hide",
                                            fontSize = 14.sp

                                        )

                                    }
                                }else{
                                    IconButton(onClick = {showPassword = true}) {
                                         Icon(
                                              imageVector = Icons.Filled.VisibilityOff,
                                              contentDescription = "Hide password"
                                          )


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

                    if (registerPassword.length in 1..6){
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password
                    var confirmPasswordState by rememberSaveable { mutableStateOf(false) }
                    Column {
                        TextField(
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                            },
                            label = {
                                Text(
                                    text = "Confirm Password",
                                    style = MaterialTheme.typography.body2

                                )
                            },

                            visualTransformation = if (confirmPasswordState) VisualTransformation.None else
                                PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next

                            ),

                            leadingIcon ={
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon")

                                }
                            },
                            trailingIcon = {
                                if (confirmPasswordState){
                                    IconButton(onClick = {confirmPasswordState = false}) {

                                   Icon(
                                       imageVector = Icons.Filled.Visibility,
                                       contentDescription = "Hide password"
                                   )

                                    }
                                }else{
                                    IconButton(onClick = {confirmPasswordState = true}) {

                                   Icon(
                                       imageVector = Icons.Filled.VisibilityOff,
                                       contentDescription = "Hide password"
                                   )


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
                    Column {


                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = colorResource(id = R.color.background))
                                .clickable {


                                    if (fullName.length < 6) {

                                        showMessage(
                                            context,
                                            message = "Full name must be more than 6 characters"
                                        )
                                    }
                                    if (userName.length < 3) {

                                        showMessage(
                                            context,
                                            message = "Username must be more than 3 characters"
                                        )

                                    }
                                    if (!email.contains("@")) {

                                        showMessage(context, message = "Invalid email address")
                                    }
                                    if (
                                        registerPassword != confirmPassword || registerPassword.length < 6
                                    ) {
                                        showMessage(context, message = "passwords don't much")

                                    }
                                    if (
                                        fullName.length >= 6 && userName.length >= 3
                                        && email.contains("@") && (registerPassword == confirmPassword
                                                && registerPassword.length >= 6)

                                    ) {

                                        scope.launch {
                                           showMessage(context, "Registering ...")
                                            val account = createAccount(
                                                username = userName,
                                                fullName = fullName,
                                                email = email,
                                                password =  registerPassword
                                            )

                                            if (account == "user created"){
                                                showMessage(context, "Registration is successful")
                                                navController.popBackStack()
                                                navController.popBackStack()
                                                navController.navigate(Screen.Dashboard.route)

                                            }
                                            else if(account == "user exists"){
                                                showMessage(context, "Email already exists")
                                            }
                                            else if(!internet){
                                                showMessage(context, "No Internet connection")
                                            }
                                            else if(account == null){
                                                showMessage(context, "Registration failed ")

                                            }


                                        }

                                    }


                                }

                        ) {
                            Text(
                                text = "Create Account",
                                color = colorResource(id = R.color.app_white),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Normal

                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(230.dp))


                }
        }
    }

}

fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}





