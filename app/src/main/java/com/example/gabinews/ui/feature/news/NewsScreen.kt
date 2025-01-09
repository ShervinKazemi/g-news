@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gabinews.ui.feature.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.ui.feature.NewsViewModel
import com.example.gabinews.ui.widgets.SharedImage
import com.example.gabinews.util.MyScreens
import com.example.gabinews.util.UiState
import com.example.gabinews.util.filterValidNews
import com.example.gabinews.util.formatDate
import com.google.gson.Gson
import dev.burnoo.cokoin.viewmodel.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsScreen(category: String, navController: NavHostController) {
    val viewModel = getViewModel<NewsViewModel>()
    val newsState = viewModel.news.collectAsStateWithLifecycle().value

    LaunchedEffect(category) {
        viewModel.getNewsByCategory(category)
    }

    when (newsState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }

        is UiState.Success -> {
            val articles = newsState.data.filterValidNews()
            NewsList(
                articles = articles,
                category = category,
                navController = navController
            )
        }

        is UiState.Error -> {
            OfflineMessageScreen()
        }
    }
}

@Composable
fun NewsList(
    articles: List<NewsResponse.Article>,
    category: String,
    navController: NavHostController
) {
    var selectedArticle by remember { mutableStateOf<NewsResponse.Article?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        category,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles.take(20)) { article ->
                NewsItem(
                    article = article,
                    isSelected = selectedArticle == article,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun NewsItem(
    article: NewsResponse.Article,
    isSelected: Boolean,
    navController: NavHostController
) {
    val articleJson = URLEncoder.encode(Gson().toJson(article), StandardCharsets.UTF_8.toString())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                navController.navigate(MyScreens.DetailScreen.createRoute(articleJson))
            }
    ) {
        Column {
            if (article.urlToImage != null) {
                SharedImage(
                    imageUrl = article.urlToImage,
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .graphicsLayer {
                            alpha = if (isSelected) 1f else 0.8f
                            scaleX = if (isSelected) 1.1f else 1f
                            scaleY = if (isSelected) 1.1f else 1f
                        }
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Published at: " + formatDate(article.publishedAt.toString()),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Composable
fun OfflineMessageScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Black, Color(0xFF3C1053))))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "No Internet",
                tint = Color.White,
                modifier = Modifier
                    .size(64.dp)
                    .alpha(0.8f)
                    .graphicsLayer {
                        shadowElevation = 12.dp.toPx()
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Lost in the shadows of the offline world?\nReconnect to unveil the secrets waiting for you.",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.alpha(0.9f)
            )
        }
    }
}