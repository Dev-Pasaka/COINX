package online.pascarl.coinx.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import online.pascarl.coinx.R
import online.pascarl.coinx.model.BottomSheetItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapBottomSheet(navController:NavHostController,onDismiss:() -> Unit){
    val modalBottomSheetState = rememberModalBottomSheetState()
    val bottomSheetItems = listOf(
        BottomSheetItem(title = "Buy", icon = Icons.Default.Add),
        BottomSheetItem(title = "Sell", icon = Icons.Default.Payment),
        BottomSheetItem(title = "Convert", icon = Icons.Default.CompareArrows),
        BottomSheetItem(title = "Deposit", icon = Icons.Default.ArrowDownward),
        )
    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        LazyColumn{
            items(count = bottomSheetItems.size){
                Row(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            if(ISCOMINGSOONSCREENON){
                                navController.navigate(route = Screen.ComingSoon.route)
                            }else{

                            }
                        }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(shape = MaterialTheme.shapes.large)

                    ){
                        Surface(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .background(color = MaterialTheme.colorScheme.surface)
                        ) {
                            Icon(
                                imageVector = bottomSheetItems[it].icon,
                                contentDescription =bottomSheetItems[it].title,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(){
                            Text(
                                text = bottomSheetItems[it].title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            when(bottomSheetItems[it].title){
                                "Buy" ->{
                                    Text(
                                        text = "Buy crypto with kes",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                "Sell" ->{
                                    Text(
                                        text = "Sell crypto to kes",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                "Convert" ->{
                                    Text(
                                        text = "Exchange one coin for another with 0 fee",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                else ->{
                                    Text(
                                        text = "Deposit with fiat and crypto currency",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                        }
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

var ISCOMINGSOONSCREENON = true