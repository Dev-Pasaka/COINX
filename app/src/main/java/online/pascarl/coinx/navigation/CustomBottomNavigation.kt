package online.pascarl.coinx.navigation

import android.graphics.drawable.Icon
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import com.exyte.animatednavbar.AnimatedNavigationBar
import online.pascarl.coinx.R
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius

/*@Preview(showSystemUi = true)
@Composable
fun BottomNavPreview(){
    BottomNavigation()
}*/

@Composable
fun CustomBottomNavigation(
    navController: NavHostController,
    bottomBarViewModel: BottomBarViewModel
){
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()

    ){
        AnimatedNavigationBar(
            barColor = colorResource(id = R.color.background),
            ballColor = colorResource(id = R.color.background),
            cornerRadius  = shapeCornerRadius(2f),
            ballAnimation = Teleport(tween(300)),
            indentAnimation = Height(tween(300)),
            selectedIndex = bottomBarViewModel.selectedIndex,
        ) {
            bottomBarViewModel.bottomNavigationItems.forEach {
                BottomNavigationItems(
                    index = it.index,
                    title = it.title,
                    icon = it.icon,
                    navController = navController,
                    bottomBarViewModel = bottomBarViewModel
                )
            }
        }
        
    }
}

@Composable
fun BottomNavigationItems(
    index: Int,
    title: String,
    icon: ImageVector,
    navController: NavHostController,
    bottomBarViewModel: BottomBarViewModel
) {
    val selectedColor = Color.White
    val notSelectedColor = Color.White.copy(alpha = 0.3f)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)

    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable {
                bottomBarViewModel.navigateTo(index = index)
                navController.popBackStack()
                navController.navigate(bottomBarViewModel.route)
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (bottomBarViewModel.selectedIndex == index) selectedColor
                else notSelectedColor
            )
            Text(
                text = title,
                color = if (bottomBarViewModel.selectedIndex == index) selectedColor
                else notSelectedColor,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Normal
            )
        }


    }
}