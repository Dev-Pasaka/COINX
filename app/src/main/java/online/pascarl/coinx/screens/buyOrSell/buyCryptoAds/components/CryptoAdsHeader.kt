package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CryptoAdsHeader(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(RoundedCornerShape(360.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(360.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                ){
                    navController.popBackStack()
                }
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(2.dp)
                    .size(20.dp)
            )
        }

    }
}