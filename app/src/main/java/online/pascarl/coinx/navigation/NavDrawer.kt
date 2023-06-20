package online.pascarl.coinx.navigation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import online.pascarl.coinx.R
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.auth_screen.showMessage
import online.pascarl.coinx.screens.bottom_bar_navigation.*


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavDrawer(
    navController: NavHostController, 
    bottomBarViewModel: BottomBarViewModel = viewModel()
) {
    val navController1 = rememberNavController()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {
        Scaffold(
            drawerContent = { NavigationDrawer(navController = navController)},
        ) {

        }
    }

}

@Composable
fun NavigationDrawer(
    navController: NavHostController,
    bottomNavigationContainerViewModel: NavDrawerViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 0.dp))
                .background(color = colorResource(id = R.color.background))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)

            ) { 
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person_icon),
                        tint = Color.Gray,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(360.dp))
                            .background(color = colorResource(id = R.color.app_white))
                            .clickable {
                            }
                    )
                    Spacer(modifier = Modifier.width(130.dp))
                }
            }
        }
        Body(
            navController = navController, bottomNavigationContainerViewModel
        )
    }
}



@Composable
fun Body(
    navController: NavHostController,
    bottomNavigationContainerViewModel: NavDrawerViewModel
){
    val drawerItems = listOf(
        DrawerItems(icon = Icons.Default.VerifiedUser, title = "Kyc Verification"),
        DrawerItems(icon = Icons.Default.Update, title = "Update information"),
        DrawerItems(icon = Icons.Default.Share, title = "Invite friends"),
        DrawerItems(icon = Icons.Default.Phone, title = "Contact us"),
        DrawerItems(icon = Icons.Default.Settings, title = "Settings"),
        DrawerItems(icon = Icons.Default.Logout, title = "Logout"),
    )
    LazyColumn{
        items(count = drawerItems.size){
            BodyItems(
                bottomNavigationContainerViewModel = bottomNavigationContainerViewModel,
                icon = drawerItems[it].icon, title = drawerItems[it].title,
            )
            drawerItems[it]
        }
    }
    if (bottomNavigationContainerViewModel.isLogOutDialogVisible)
        LogOutDialog(
            navController = navController,
            bottomNavigationContainerViewModel = bottomNavigationContainerViewModel
        )

}




@Composable
fun BodyItems(
    icon:ImageVector, title: String,
    bottomNavigationContainerViewModel: NavDrawerViewModel,
){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                when(title){
                    "Kyc Verification" ->{
                    }
                    "Update information" -> {
                    }
                    "Invite friends" -> {
                    }
                    "Contact us" -> {
                    }
                    "Settings" -> {
                    }
                    "Logout" -> {
                        bottomNavigationContainerViewModel.showOrHideLogOutDialog()
                    }
                }
            }
        ){
            Icon(
                imageVector = icon,
                contentDescription ="Icon person",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                fontSize = 16.sp,
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LogOutDialog(
    navController:NavHostController,
    bottomNavigationContainerViewModel: NavDrawerViewModel
) {
    val context = LocalContext.current
    val roomDB = RoomViewModel(
        application = Application(),
        userRepository = UserRepository(UserDatabase.getInstance(LocalContext.current.applicationContext).userDao())
    )
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.48f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Column(
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.StopCircle,
                        contentDescription = "Warning",
                        tint = colorResource(id = R.color.cream)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Logout Confirmation",
                        style = MaterialTheme.typography.body2,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Are you sure you want to logout?",
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp, end = 32.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        text = "No thanks",
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.background),
                        modifier = Modifier.clickable {
                            bottomNavigationContainerViewModel.showOrHideLogOutDialog()
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .height(30.dp)
                            .width(70.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(color = colorResource(id = R.color.background))
                            .clickable {
                                val result = roomDB.deleteUser(id = "12345678")
                                if (result > 0) {
                                    showMessage(context, "Logging you out")
                                    navController.navigate(Screen.Register.route) {
                                        popUpTo(Screen.SplashScreen.route) {
                                            inclusive = true
                                        }
                                    }

                                }
                            }

                    ) {
                        Text(
                            text = "Yes",
                            style = MaterialTheme.typography.body2,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }

            }
            // Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth())
        }

    }

}


data class DrawerItems(
    val icon: ImageVector,
    val title:String
)