package online.pascarl.coinx.screens.bottom_bar_navigation

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.R
import online.pascarl.coinx.imageLoader
import online.pascarl.coinx.isInternetAvailable
import online.pascarl.coinx.navigation.BottomBarViewModel
import online.pascarl.coinx.navigation.CustomBottomNavigation
import online.pascarl.coinx.navigation.NavigationDrawer
import online.pascarl.coinx.screens.NoInternet


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsFeed(
    navController: NavHostController,
    newsFeedViewModel: NewsFeedViewModel = viewModel(),
    bottomBarViewModel: BottomBarViewModel = viewModel()
) {
    val context = LocalContext.current
    val isNetworkAvailable  = isInternetAvailable(context = context)
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(Unit) {
        if (isNetworkAvailable)newsFeedViewModel.getNewsArticle()
    }
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }

  //  val scaffoldState = rememberScaffoldState()

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(
                navController = navController,
            )
        }
    ) {
        if (isInternetAvailable(context = context))
            Column(
                modifier = Modifier
                    .alpha(alphaAnim.value)
            ) {
                if (newsFeedViewModel.showWebView) {
                    ReadMoreWebView(
                        newsFeedViewModel = newsFeedViewModel,
                        onLoadingFinished = { newsFeedViewModel.onLoadingFinished() }
                    )
                } else if (newsFeedViewModel.formattedArticleList.isEmpty()) {
                    NewsFeedLoadingPreview()
                } else NewsListBody(
                    navController = navController,
                    newsFeedViewModel = newsFeedViewModel
                )

            }
        else{
            NoInternet()
        }
    }


}
@Composable
fun NewsHeader() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Discover",
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Full disclaimer. This platform includes third party opinions. We do " +
                    "not endorse their accuracy. Digital asset prices can be volatile.For more " +
                    "information please check our terms and conditions.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

    }
}


@Preview(showSystemUi = true)
@Composable
fun HeaderPreview(){
    NewsHeader()
}
@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsListBody(
    navController: NavHostController,
    newsFeedViewModel: NewsFeedViewModel = viewModel()
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }
    NewsHeader()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alphaAnim.value),
        contentAlignment = Alignment.TopCenter
    ) {
        if (newsFeedViewModel.formattedArticleList.isNotEmpty())
            LazyColumn {
                items(newsFeedViewModel.formattedArticleList.size) {
                    NewsListItem(
                        title = newsFeedViewModel.formattedArticleList[it].title,
                        imageUrl = newsFeedViewModel.formattedArticleList[it].imageUrl,
                        byAuthor = newsFeedViewModel.formattedArticleList[it].byAuthor,
                        publishedDate = newsFeedViewModel.formattedArticleList[it].publishedDate,
                        externalLink = newsFeedViewModel.formattedArticleList[it].externalLink,
                        description = newsFeedViewModel.formattedArticleList[it].description,
                        newsFeedViewModel = newsFeedViewModel
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onBackground,
                        thickness = 1.dp
                    )
                }
            }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsListItem(
    title: String,
    imageUrl: String,
    byAuthor: String,
    publishedDate: String,
    externalLink: String,
    description: String,
    newsFeedViewModel: NewsFeedViewModel
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable {
                newsFeedViewModel.openWebView(url = externalLink)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "By: $byAuthor",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall

            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(painter = rememberImagePainter(data = imageUrl), contentDescription = null)
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "Article front image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,

                )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Published: $publishedDate",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            newsFeedViewModel.openWebView(url = externalLink)
                        }
                ) {
                    Text(
                        text = "Read more",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

        }


    }
}

@Composable
fun ReadMoreWebView(
    newsFeedViewModel: NewsFeedViewModel,
    onLoadingFinished: () -> Unit
) {
    val context = LocalContext.current

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(top = 16.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (newsFeedViewModel.isLoading) {
                            newsFeedViewModel.isLoading = false
                            onLoadingFinished() // Callback indicating loading finished
                        }
                    }
                }
                loadUrl(newsFeedViewModel.webViewUrl)
            }
        })
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 16.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 18.dp,
                focusedElevation = 18.dp
            ),
            modifier = Modifier
                .padding(top = 16.dp),
            onClick = { newsFeedViewModel.closeWebView() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }

}

@Composable
fun WebViewLoadingPreview(newsFeedViewModel: NewsFeedViewModel) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fetching))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.LightGray)
            .alpha(alphaAnim.value)

    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
        )

    }

}

@Composable
fun NewsFeedLoadingPreview() {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fetching))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.background)
                .alpha(alphaAnim.value)

        ) {

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            )

        }
    }
}




