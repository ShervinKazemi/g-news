package com.example.gabinews.util

import android.util.Log
import com.example.gabinews.model.data.NewsResponse.Article
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.Locale

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("CoroutineException", "Error: ${throwable.message}", throwable)
}

fun Article.isValid() :Boolean {
    return this.title != "[Removed]" && this.content != "[Removed]" && this.urlToImage != null
}

fun List<Article>.filterValidNews(): List<Article> {
    return this.filter { it.isValid() }
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}