package com.example.gabinews.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("homeScreen")
}