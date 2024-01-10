package online.pascarl.coinx.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.runtime.Composable

import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patrykandpatrick.vico.core.extension.setFieldValue


@Composable
fun CustomBottomNavigation(navController: NavHostController) {
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        SwapBottomSheet(navController = navController) {
            showSheet = false
        }
    }
    Row(
        modifier = Modifier

    ) {
        Column {
            Divider(
                thickness = 1.5.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()

            ) {
                bottomScreens.forEach {

                    if (it.title.isNotBlank()){
                        CustomBottomBarItems(
                            title = it.title,
                            route = it.route,
                            icon = it.icon,
                            index = it.index,
                            navController = navController
                        )

                    }else{
                        Column(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(color = MaterialTheme.colorScheme.tertiary)
                                .clickable {
                                    showSheet = true
                                }
                        ) {
                            Icon(
                                imageVector = Swap().icon,
                                contentDescription = Swap().route,
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun CustomBottomBarItems(
    title:String,
    icon:ImageVector,
    route:String,
    index:Int,
    navController: NavHostController
){

    Surface(
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable(enabled = index != `SELECTED-ITEM`) {
                navController.navigate(bottomScreens[index].route) {
                    popUpTo(route) { inclusive = true }
                    `SELECTED-ITEM` = bottomScreens[index].index
                }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)

        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if(`SELECTED-ITEM` == index) MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)

            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = if(`SELECTED-ITEM` == index) MaterialTheme.colorScheme.onBackground
                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)

            )
        }
    }

}
val bottomScreens = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Feed,
    BottomBarScreen.Swap,
    BottomBarScreen.Orders,
    BottomBarScreen.Wallet
)
var `SELECTED-ITEM` = 0