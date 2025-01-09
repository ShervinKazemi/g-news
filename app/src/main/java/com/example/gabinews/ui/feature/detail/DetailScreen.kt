@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gabinews.ui.feature.detail

import android.widget.ScrollView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.gabinews.model.data.NewsResponse
import com.example.gabinews.ui.feature.HeroViewModel
import com.example.gabinews.ui.widgets.HeroImage
import com.example.gabinews.util.formatDate
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(
    articleJson: String,
    navController: NavHostController,
    heroViewModel: HeroViewModel
) {
    val decodedJson = URLDecoder.decode(articleJson, StandardCharsets.UTF_8.toString())
    val article = Gson().fromJson(decodedJson, NewsResponse.Article::class.java)
    val currentArticle by heroViewModel.currentArticle.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        heroViewModel.startHeroAnimation(article)
        onDispose {
            heroViewModel.endHeroAnimation()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = article.author.orEmpty())
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Make the entire screen scrollable
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (article.urlToImage != null) {
                HeroImage(
                    imageUrl = article.urlToImage,
                    contentDescription = "News Image",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            // Content of the article
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatDate(article.publishedAt.orEmpty()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = article.content.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

