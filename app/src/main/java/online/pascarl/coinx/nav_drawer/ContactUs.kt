package online.pascarl.coinx.nav_drawer

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import online.pascarl.coinx.ui.theme.Shapes

@Preview(showSystemUi = true)
@Composable
fun ContactUsPreview() {
    val navController = rememberNavController()
    ContactUs(navController = navController)
}

@Composable
fun ContactUs(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val contactUsViewModel = viewModel<ContactUsViewModel>()
    contactUsViewModel.isDarkMode = isSystemInDarkTheme()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(state = scrollState)
    ) {
        ContactUsBody(
            navController = navController,
            contactUsViewModel = contactUsViewModel,
            darkTheme = contactUsViewModel.isDarkMode
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsBody(
    navController: NavHostController,
    contactUsViewModel: ContactUsViewModel,
    darkTheme:Boolean
) {
    val context = LocalContext.current
    Box() {


        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Contact us",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                )


            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Chat with us for immediate assistance",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Physical address",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = {  contactUsViewModel.launchMap(context = context)}
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                    ) {
                        Text(
                            text = """
                        West park towers,
                        11 floor,
                        Westlands, Nairobi, Kenya
                    """.trimIndent(),
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.LocationSearching,
                        contentDescription = "Map",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .padding(16.dp)
                            .size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                            ) {
                                contactUsViewModel.launchMap(context = context)
                            }
                    )


                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Contact us",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Our Contacts",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = { /*TODO*/ }
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Telephone",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "+254717722324",
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                            ) {
                                contactUsViewModel.launchPhoneDial(context = context)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Email",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "support@coinx.co.ke",
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true),
                            ) {
                                contactUsViewModel.launchEmail(context = context)
                            }

                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    // .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Follow us:",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                        }
                ) {

                    Image(
                        painter = if(darkTheme) painterResource(id = contactUsViewModel.facebookDarkIcon)
                        else painterResource(id = contactUsViewModel.facebookLightIcon),
                        contentDescription = "Facebook",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .padding(8.dp)
                            .size(25.dp)

                    )
                }
                Column(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                        }
                ) {
                    Image(
                        painter = if(darkTheme) painterResource(id = contactUsViewModel.instagramDarkIcon)
                        else painterResource(id = contactUsViewModel.instagramLightIcon),
                        contentDescription = "Instagram",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .padding(8.dp)
                            .size(25.dp)

                    )
                }
                Column(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                        }
                ) {
                    Image(
                        painter = if(darkTheme) painterResource(id = contactUsViewModel.twitterDarkIcon)
                        else painterResource(id = contactUsViewModel.twitterLightIcon ),
                        contentDescription = "Twitter",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .padding(8.dp)
                            .size(25.dp)

                    )
                }
                Column(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true),
                        ) {

                        }
                ) {
                    Image(
                        painter = if(darkTheme) painterResource(id = contactUsViewModel.websiteDarkIcon)
                        else painterResource(id = contactUsViewModel.websiteLightIcon),
                        contentDescription = "Website",
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .padding(8.dp)
                            .size(25.dp)

                    )
                }

            }

        }
    }
    Spacer(modifier = Modifier.height(48.dp))
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.HeadsetMic,
                    contentDescription = "Chat",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .padding(8.dp)
                        .size(30.dp)

                )

            }
            Text(
                text = "Chat",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}


@Composable
fun DialPhoneScreen(phoneNumber: String) {
    val context = LocalContext.current as ComponentActivity
    val dialerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result, if needed
    }

    /*val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }*/

   // dialerLauncher.launch(intent)

    // Here, you can design the rest of your Composable UI for the DialPhoneScreen
    // For example, you can display a "Dialing..." message or any other content.
}