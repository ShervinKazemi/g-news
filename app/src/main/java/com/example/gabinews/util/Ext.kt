package com.example.gabinews.util

import android.util.Log
import com.example.gabinews.model.data.NewsResponse.Article
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("CoroutineException", "Error: ${throwable.message}", throwable)
}

fun Article.isValid() :Boolean {
    return this.source.name != "[Removed]" && title.isNotBlank() && content.isNotBlank()
}

fun List<Article>.filterValidNews(): List<Article> {
    return this.filter { it.isValid() }
}