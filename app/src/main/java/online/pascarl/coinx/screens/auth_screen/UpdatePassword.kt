package online.pascarl.coinx.screens.auth_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.navigation.Screen

/*
@Preview(showSystemUi = true)
@Composable
fun UpdatePasswordPreview(updatePasswordViewModel: UpdatePasswordViewModel = viewModel()){
    UpdatePassword(updatePasswordViewModel = updatePasswordViewModel)
}*/

@Composable()
fun UpdatePassword(navController: NavHostController, updatePasswordViewModel: UpdatePasswordViewModel = viewModel()){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        UpdatePasswordHeader(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        NewPassword(updatePasswordViewModel = updatePasswordViewModel)
        ConfirmNewPassword(updatePasswordViewModel = updatePasswordViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        ChangePasswordButton(navController = navController, updatePasswordViewModel = updatePasswordViewModel)
    }
}

@Composable
fun UpdatePasswordHeader(navController: NavHostController){
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
                text = "Login",
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
fun NewPassword(updatePasswordViewModel: UpdatePasswordViewModel){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
        OutlinedTextField(
            value = updatePasswordViewModel.newPassword,
            onValueChange = {updatePasswordViewModel.newPassword = it},
            label = {
                Text(
                    text = "New Password",
                    style = MaterialTheme.typography.body2

                )
            },

            visualTransformation = if (updatePasswordViewModel.showPassword)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next

            ),
            leadingIcon ={ Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon") },
            trailingIcon = {
                IconButton(onClick = { updatePasswordViewModel.hideNewPassword()}) {
                    Icon(
                        imageVector = if(updatePasswordViewModel.showPassword)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password icon"
                    )
                }
            },
            isError = updatePasswordViewModel.formValidationPassed,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = colorResource(id = R.color.background),
                placeholderColor = colorResource(id = R.color.background),
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()


        )

    }

}
@Composable
fun ConfirmNewPassword(updatePasswordViewModel: UpdatePasswordViewModel){
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
        OutlinedTextField(
            value = updatePasswordViewModel.confirmNewPassword,
            onValueChange = {updatePasswordViewModel.confirmNewPassword = it},
            label = {
                Text(
                    text = "Confirm New Password",
                    style = MaterialTheme.typography.body2
                )
            },

            visualTransformation = if (updatePasswordViewModel.showConfirmNewPassword)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next

            ),
            leadingIcon ={ Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password icon") },
            trailingIcon = {
                IconButton(onClick = { updatePasswordViewModel.hideConfirmNewPassword()}) {
                    Icon(
                        imageVector = if(updatePasswordViewModel.showConfirmNewPassword)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Password icon"
                    )
                }
            },
            isError = updatePasswordViewModel.formValidationPassed,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = R.color.background),
                unfocusedIndicatorColor = colorResource(id = R.color.background),
                placeholderColor = colorResource(id = R.color.background),
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()


        )

    }

}
@Composable
fun ChangePasswordButton(navController: NavHostController, updatePasswordViewModel: UpdatePasswordViewModel){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(50))
            .background(
                color = if (
                    updatePasswordViewModel.newPassword.isBlank()
                    || updatePasswordViewModel.confirmNewPassword.isBlank()
                ) Color.LightGray else colorResource(id = R.color.background)
            )
            .clickable(
                enabled = updatePasswordViewModel.newPassword.isNotBlank()
                && updatePasswordViewModel.confirmNewPassword.isNotBlank()
            ) {
                scope.launch {
                    updatePasswordViewModel.updatePassword()
                    if (!updatePasswordViewModel.formValidationPassed){
                        if (updatePasswordViewModel.isPasswordUpdateSuccessful){
                              navController.popBackStack()
                               navController.navigate(Screen.EmailResetConfirmation.route)
                            showMessage(context, "Password Updated Successful")
                        }
                        else showMessage(context, "Password Update failed")
                    }
                }
            }


    ){
        Text(
            text = "Change Password",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}