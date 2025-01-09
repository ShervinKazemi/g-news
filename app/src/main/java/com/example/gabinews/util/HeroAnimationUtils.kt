package com.example.gabinews.util

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

private const val ANIMATION_DURATION = 500

@Composable
fun AnimatedNewsImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false
) {
    AnimatedContent(
        targetState = isExpanded,
        transitionSpec = {
            ContentTransform(
                targetContentEnter = scaleIn(
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeIn(
                    animationSpec = tween(ANIMATION_DURATION)
                ),
                initialContentExit = scaleOut(
                    animationSpec = tween(ANIMATION_DURATION)
                ) + fadeOut(
                    animationSpec = tween(ANIMATION_DURATION)
                )
            )
        },
        label = "news_image"
    ) { expanded ->
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = if (expanded) ContentScale.Fit else ContentScale.Crop
        )
    }
}