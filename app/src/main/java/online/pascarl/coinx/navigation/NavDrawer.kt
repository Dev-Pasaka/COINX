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
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.auth_screen.showMessage
import online.pascarl.coinx.screens.bottom_bar_navigation.*


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavDrawer(
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerItems = listOf(
        DrawerItems(icon = Icons.Default.VerifiedUser, title = "Kyc Verification"),
        DrawerItems(icon = Icons.Default.Update, title = "Update information"),
        DrawerItems(icon = Icons.Default.Share, title = "Invite friends"),
        DrawerItems(icon = Icons.Default.Phone, title = "Contact us"),
        DrawerItems(icon = Icons.Default.Settings, title = "Settings"),
        DrawerItems(icon = Icons.Default.Logout, title = "Logout"),
    )
    val selectedItem = remember { mutableStateOf(drawerItems[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
           /* Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                Spacer(Modifier.height(20.dp))
                Button(onClick = { scope.launch { drawerState.open() } }) {
                    Text("Click to open")
                }
            }*/
        }
    )


    /*  Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Bottom,
          modifier = Modifier.fillMaxHeight()
      ) {
          Scaffold(
              //drawerContent = { NavigationDrawer(navController = navController)},
          ) {

          }
      }*/

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
        ElevatedCard(
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

                            }

                    ) {
                        Text(
                            text = "Yes",
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
    var icon: ImageVector,
    var title:String
)