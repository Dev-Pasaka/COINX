package online.pascarl.coinx.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.datasource.userData
import online.pascarl.coinx.screens.bottom_bar_navigation.*


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomBarNavigationContainer(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {
        Scaffold(
            bottomBar = { BottomBarNavigation(navController = navController) },
        ) {
            BottomNavGraph(navController = navController)

        }
    }

}


@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val bottomBarScreenItems = bottomNavigationItems
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.background)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomBarScreenItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = "Icon") },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(alpha = 0.3f),
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) }
            )

        }

    }
}

@Composable
fun NavigationDrawer(
    username: String = userData.username,
    isVerified: Boolean = true
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        
                        painter = painterResource(id = R.drawable.person_icon),
                        tint = Color.Gray,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(360.dp))
                            .background(color = colorResource(id = R.color.app_white))
                            .clickable {
                            }
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    ) {

                        Text(
                            text = username,
                            color = colorResource(id = R.color.app_white),
                            style = MaterialTheme.typography.body2,
                            fontSize = 18.sp,
                        )
                        Text(
                            text = if (isVerified) "Verified" else "Unverified",
                            color = if (isVerified) Color.Green else Color.Yellow,
                            style = MaterialTheme.typography.body2,
                            fontSize = 14.sp,
                        )
                    }
                    Spacer(modifier = Modifier.width(150.dp))
                    Column() {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            tint = Color.White,
                            contentDescription = "Profile Icon",
                            modifier = Modifier
                                .clip(RoundedCornerShape(360.dp))
                                .clickable {
                                }
                        )

                    }
                }
            }
        }
        Body()
    }
}



@Composable
fun Body(){
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
            BodyItems(icon = drawerItems[it].icon, title = drawerItems[it].title)
        }
    }
}


@Composable
fun BodyItems(icon:ImageVector, title: String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .clickable {

            }
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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

data class DrawerItems(
    val icon: ImageVector,
    val title:String
)