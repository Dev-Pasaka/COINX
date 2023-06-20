package online.pascarl.spx.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.rememberImeState
import online.pascarl.coinx.screens.auth_screen.CircularProgressBar
import online.pascarl.coinx.screens.auth_screen.CreateAccountViewModel
import online.pascarl.coinx.screens.auth_screen.showMessage
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreateAccount(
    navController: NavHostController,
    createAccountViewModel: CreateAccountViewModel = viewModel()
) {
    DisableBackNavigation()
    when (createAccountViewModel.registerScreenIndex) {
        0 -> {
            var startAnimation by remember {
                mutableStateOf(false)
            }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
            LaunchedEffect(key1 = true){
                startAnimation = true
                delay(1000)
            }

            FirstRegistrationScreen(
                    navController = navController,
                    createAccountViewModel = createAccountViewModel,
                alpha = alphaAnim.value
                )

        }
        1 -> {
            var startAnimation by remember {
                mutableStateOf(false)
            }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
            LaunchedEffect(key1 = true){
                startAnimation = true
                delay(1000)
            }
                SecondRegistrationScreen(
                    navController = navController,
                    createAccountViewModel = createAccountViewModel,
                    alpha = alphaAnim.value
                )
            }
        2 -> {
            var startAnimation by remember {
                mutableStateOf(false)
            }
            val alphaAnim = animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
            LaunchedEffect(key1 = true){
                startAnimation = true
                delay(1000)
            }
            ThirdRegistrationScreen(
                navController = navController,
                createAccountViewModel = createAccountViewModel,
                alpha = alphaAnim.value
            )
        }


    }
}


@Composable
fun FullName(createAccountViewModel: CreateAccountViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = createAccountViewModel.fullName,
            onValueChange = {
                createAccountViewModel.fullName = it
            },
            label = {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.body2,

                    )
            },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
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
}

@Composable
fun Username(createAccountViewModel: CreateAccountViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = createAccountViewModel.username,
            onValueChange = {
                createAccountViewModel.username = it
            },
            label = {
                Text(
                    text = "Username",
                    style = MaterialTheme.typography.body2,

                    )
            },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
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
}

@Composable
fun Email(createAccountViewModel: CreateAccountViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = createAccountViewModel.email,
            onValueChange = {
                createAccountViewModel.email = it
            },
            label = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.body2

                )
            },
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
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhoneNumber(createAccountViewModel: CreateAccountViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = createAccountViewModel.formatedPhoneNumber,
            onValueChange = {
                createAccountViewModel.formatedPhoneNumber = it
            },
            label = {
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.body2,
                )
            },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
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
}

@Composable
fun Password(createAccountViewModel: CreateAccountViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = createAccountViewModel.password,
            onValueChange = {
                createAccountViewModel.password = it
            },
            label = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.body2

                )
            },

            visualTransformation = if (createAccountViewModel.showPassword)
                VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { createAccountViewModel.hidePassword() }) {
                    Icon(
                        imageVector = if (createAccountViewModel.showPassword)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password icon"
                    )
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

}

@Composable
fun ConfirmPassword(createAccountViewModel: CreateAccountViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = createAccountViewModel.confirmPassword,
            onValueChange = {
                createAccountViewModel.confirmPassword = it
            },
            label = {
                Text(
                    text = "Confirm Password",
                    style = MaterialTheme.typography.body2

                )
            },

            visualTransformation = if (createAccountViewModel.showConfirmPassword)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next

            ),
            trailingIcon = {
                IconButton(onClick = { createAccountViewModel.hideConfirmPassword() }) {
                    Icon(
                        imageVector = if (createAccountViewModel.showConfirmPassword)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password icon"
                    )
                }
            },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            singleLine = true,
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
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun FirstRegistrationScreen(
    navController: NavHostController,
    createAccountViewModel: CreateAccountViewModel,
    alpha:Float
) {
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(state = scrollState)
            .scrollable(scrollState, Orientation.Vertical)
            .alpha(alpha)


    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Go Back",
                tint = colorResource(id = R.color.background),
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.cream)
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Register your name and username below",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(top = 20.dp)
            )
            Image(
                painter = rememberImagePainter(
                    data = "https://img.freepik.com/free-vector/cryptocurrency-trading-desk-abstract" +
                            "-concept-illustration_335657-2170.jpg?w=740&t=st=1687206080~exp=" +
                            "1687206680~hmac=" +
                            "0f41638793942d93498b500e512ab8e09442ac6e5c22f2702d80b4c9d4e3ce47"
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(100))
            )
            FullName(createAccountViewModel = createAccountViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Username(createAccountViewModel = createAccountViewModel)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        color = if (createAccountViewModel.fullName.isNotBlank()
                            && createAccountViewModel.username.isNotBlank()
                        )
                            colorResource(id = R.color.background) else
                            colorResource(id = R.color.light_gray)
                    )
                    .clickable(
                        enabled = createAccountViewModel.fullName.isNotBlank() &&
                                createAccountViewModel.username.isNotBlank()
                    ) {
                        createAccountViewModel.next()
                    }
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Composable
fun SecondRegistrationScreen(
    navController: NavHostController,
    createAccountViewModel: CreateAccountViewModel,
    alpha:Float
) {
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(state = scrollState)
            .scrollable(scrollState, Orientation.Vertical)
            .alpha(alpha)


    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Go Back",
                tint = colorResource(id = R.color.background),
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        createAccountViewModel.back()
                    }
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.cream)
            )
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Cance",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Enter your Email and Phone number below",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(top = 20.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.btc_staking),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(100))
            )
            Email(createAccountViewModel = createAccountViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            PhoneNumber(createAccountViewModel = createAccountViewModel)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        color = if (createAccountViewModel.email.contains("@")
                            && createAccountViewModel.phoneNumber.length >= 10
                        )
                            colorResource(id = R.color.background)
                        else colorResource(id = R.color.light_gray)
                    )
                    .clickable(
                        enabled = createAccountViewModel.email.contains("@") &&
                                createAccountViewModel.phoneNumber.length >= 10
                    ) {
                        createAccountViewModel.next()
                    }
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

        }
    }
}

@Composable
fun ThirdRegistrationScreen(
    navController: NavHostController,
    createAccountViewModel: CreateAccountViewModel,
    alpha:Float
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(500))
        }
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sign_up))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(state = scrollState)
            .scrollable(scrollState, Orientation.Vertical)
            .alpha(alpha)


    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Go Back",
                tint = colorResource(id = R.color.background),
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        createAccountViewModel.back()
                    }
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.cream)
            )
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = "Cancel",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Password Confirmation",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.padding(top = 20.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
            Password(createAccountViewModel = createAccountViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            ConfirmPassword(createAccountViewModel = createAccountViewModel)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (createAccountViewModel.showCircularProcessBar) CircularProgressBar()
            else Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .width(100.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        color = if (
                            createAccountViewModel.password == createAccountViewModel.confirmPassword
                            && createAccountViewModel.password.isNotBlank() && createAccountViewModel.confirmPassword.isNotBlank()
                        ) colorResource(id = R.color.background) else colorResource(id = R.color.light_gray)

                    )
                    .clickable(
                        enabled = createAccountViewModel.password == createAccountViewModel.confirmPassword
                                && createAccountViewModel.password.isNotBlank() && createAccountViewModel.confirmPassword.isNotBlank()

                    ) {
                        scope.launch {
                            when (createAccountViewModel.createAccount()) {
                                "user created" -> {
                                    navController.popBackStack()
                                    showMessage(context, "Registration Successful")
                                }

                                "user exists" -> showMessage(context, "User already exists")
                                null -> showMessage(context, "Registration failed")
                            }
                        }
                    }
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 14.sp
                )

            }

        }


    }
}











@Composable
fun DisableBackNavigation() {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back navigation if needed
                // To disable back navigation completely, leave this block empty
            }
        }
        backDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}







