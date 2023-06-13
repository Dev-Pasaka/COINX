package online.pascarl.spx.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import online.pascarl.coinx.Country
import online.pascarl.coinx.R
import online.pascarl.coinx.getFlagEmojiFor
import online.pascarl.coinx.navigation.Screen
import online.pascarl.coinx.screens.auth_screen.CircularProgressBar
import online.pascarl.coinx.screens.auth_screen.CreateAccountViewModel

@Composable
fun CreateAccount(
    createAccountViewModel: CreateAccountViewModel = viewModel(),
    navController: NavHostController
){
    val scrollState = rememberScrollState()
    Column {
        Header(navController = navController)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Register",
                fontSize = 16.sp,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 5.dp),
            )
            if (createAccountViewModel.showCircularProcessBar) CircularProgressBar()
            Spacer(modifier = Modifier.height(5.dp))
        }
       Column(modifier = Modifier.verticalScroll(state = scrollState)) {
           FullName(createAccountViewModel = createAccountViewModel)
           Username(createAccountViewModel = createAccountViewModel)
           Email(createAccountViewModel = createAccountViewModel)
           PhoneNumber(createAccountViewModel = createAccountViewModel)
           Password(createAccountViewModel = createAccountViewModel)
           ConfirmPassword(createAccountViewModel = createAccountViewModel)
           RegisterAccount(createAccountViewModel = createAccountViewModel, navController = navController)
       }

    }




}
@Composable
fun Header(navController: NavHostController) {
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
            Text(
                text = "login",
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
fun FullName(createAccountViewModel: CreateAccountViewModel){
  Column(
      modifier = Modifier
          .padding(start = 16.dp, end = 16.dp, top = 10.dp)
          .fillMaxWidth()
  ){
     OutlinedTextField(
          value = createAccountViewModel.fullName,
          onValueChange = {
              createAccountViewModel.fullName = it
          },
          label = {
              Text(
                  text = "Full Name",
                  style = MaterialTheme.typography.body2,

                  )
          },
          singleLine = true,
          leadingIcon = { Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Email icon") },
          keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Text,
              imeAction = ImeAction.Next
          ),
          colors =TextFieldDefaults.textFieldColors(
              focusedIndicatorColor = colorResource(id = R.color.background),
              unfocusedIndicatorColor = Color.Transparent
          ),
          isError = createAccountViewModel.isFullNameValid,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth(),
      )
  }
}

@Composable
fun Username(createAccountViewModel: CreateAccountViewModel){
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            .fillMaxWidth()
    ){
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
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Create account") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = createAccountViewModel.isUsernameValid,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Email(createAccountViewModel: CreateAccountViewModel){
   Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
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
           singleLine = true,
           leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon") },
           keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Email,
               imeAction = ImeAction.Next
           ),
           isError = createAccountViewModel.isEmailValid,
           colors =TextFieldDefaults.textFieldColors(
               focusedIndicatorColor = colorResource(id = R.color.background),
               unfocusedIndicatorColor = Color.Transparent
           ),
           shape = RoundedCornerShape(10.dp),
           modifier = Modifier.fillMaxWidth(),
       )
   }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhoneNumber(createAccountViewModel: CreateAccountViewModel){
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
            .fillMaxWidth()
    ){
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
            textStyle =  LocalTextStyle.current.copy(color = Color.Gray),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Phone Icon"
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            isError = createAccountViewModel.isPhoneValid,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )

    }
}
@Composable
fun Password(createAccountViewModel: CreateAccountViewModel){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next

            ),
            leadingIcon ={ Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon") },
            trailingIcon = {
                IconButton(onClick = { createAccountViewModel.hidePassword()}) {
                    Icon(
                        imageVector = if(createAccountViewModel.showPassword)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password icon"
                    )
                }
            },
            isError = createAccountViewModel.isPasswordValid,
            colors =TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()


        )

    }

}

@Composable
fun ConfirmPassword(createAccountViewModel: CreateAccountViewModel){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
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
            leadingIcon ={Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon")},
            trailingIcon = {IconButton(onClick = { createAccountViewModel.hideConfirmPassword()}) {
                Icon(
                    imageVector = if(createAccountViewModel.showConfirmPassword)
                        Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Password icon"
                )
            }},
            isError = createAccountViewModel.isPasswordValid,
            colors =TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()


        )
    }
}

@Composable
fun RegisterAccount(createAccountViewModel: CreateAccountViewModel, navController: NavHostController){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
        if (createAccountViewModel.isFullNameValid) showMessage(context, message = "Full name must be more than 6 characters")
        if (createAccountViewModel.isUsernameValid)  showMessage(context, message = "Username must be more than 3 characters")
        if (createAccountViewModel.isEmailValid)  showMessage(context, message = "Invalid email address")
        if (createAccountViewModel.isPhoneValid) showMessage(context, message = "Invalid phone number")
        if (createAccountViewModel.isPasswordValid) showMessage(context, message = "Passwords don't much or less than six characters")
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = colorResource(id = R.color.background))
                .clickable {
                    createAccountViewModel.formValidation()
                    scope.launch {
                        when (createAccountViewModel.createAccount()) {
                            "user created" -> {
                                navController.navigate(Screen.Register.route)
                                showMessage(context, "Registration Successful")
                            }
                            "user exists" -> showMessage(context, "User already exists")
                            null -> showMessage(context, "Registration failed")
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
}


/*@Composable
fun DialogContent(
    countries: List<Country>,
    onCountrySelected: (Country) -> Unit
) {
    // Replace with your own dialog content implementation
    // Here's a simple example using a LazyColumn to display the country list
    LazyColumn {
        items(countries) { country ->
            Text(
                text = "${getFlagEmojiFor(country.code)} ${country.fullName}",
                modifier = Modifier
                    .clickable { onCountrySelected(country) }
                    .padding(16.dp)
            )
        }
    }
}*/
fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}







