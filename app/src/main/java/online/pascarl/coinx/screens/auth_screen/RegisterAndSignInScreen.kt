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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.rememberImeState
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    image: Painter = painterResource(id = R.drawable.coinx),
    signInViewModel: SignInViewModel = viewModel()

) {
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(
            UserDatabase.getInstance(LocalContext.current.applicationContext).userDao()
        )
    )

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var showPassword by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val imeState = rememberImeState()
    val isInternetAvailable = isInternetAvailable(context = context)


    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = scrollState)


    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Coinx",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Free . Secure . Fast . Trading",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 16.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                /** Email Text Field*/
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = signInViewModel.email,
                    onValueChange = { signInViewModel.email = it },
                    placeholder = { Text(text = "Email") },
                    isError = !signInViewModel.isSignInSuccessful(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        //textColor = MaterialTheme.colorScheme.onSurface,
                        //placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        errorIndicatorColor = MaterialTheme.colorScheme.error,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                /** Password text-field */
                OutlinedTextField(
                    value = signInViewModel.password,
                    onValueChange = {
                        signInViewModel.password = it
                    },
                    placeholder = { Text(text = "Password") },
                    isError = !signInViewModel.isSignInSuccessful(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        if (showPassword) {
                            IconButton(onClick = { showPassword = false }) {

                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = "Hide password"
                                )
                                //Text(text = "hide")

                            }
                        } else {
                            IconButton(onClick = { showPassword = true }) {

                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = "Hide password"
                                )


                            }
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        errorIndicatorColor = MaterialTheme.colorScheme.error,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,

                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Forgot password?",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                navController.navigate(Screen.ResetPassword.route)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                /** Sign In Button */
                FilledTonalButton(
                    onClick = {
                        scope.launch {
                            //Check Internet Connection
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
                                    navController.navigate(Screen.Dashboard.route){
                                       navController.popBackStack()
                                       navController.popBackStack()
                                    }
                                } else {
                                    roomDB.updateUser(
                                        user = RoomUser(
                                            email = signInViewModel.email,
                                            token = signInViewModel.backendAuthToken!!
                                        )
                                    )
                                    showMessage(context, "Signing in ...")
                                    navController.navigate(Screen.Dashboard.route){
                                        navController.popBackStack()
                                        navController.popBackStack()
                                    }
                                }
                            }else if(!isInternetAvailable)
                                showMessage(context, "No Internet connection")
                                else {
                                showMessage(context, "Invalid email or password")
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    enabled = signInViewModel.password.isNotBlank() && signInViewModel.email.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,

                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,

                        )

                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.CreateAccount.route)
                        }

                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    if (signInViewModel.circularProgressBar) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier
                                .size(30.dp)
                        )

                    }

                }


            }
        }


    }
}

@Composable
fun CircularProgressBar() {
    ElevatedCard(
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(100))

    ) {
        CircularProgressIndicator(
            strokeWidth = 3.dp,
            modifier = Modifier
                .requiredHeight(25.dp)
                .requiredWidth(25.dp)
        )

    }

}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}



