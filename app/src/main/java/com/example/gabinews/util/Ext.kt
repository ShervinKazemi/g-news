package com.example.gabinews.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("CoroutineException", "Error: ${throwable.message}", throwable)
}