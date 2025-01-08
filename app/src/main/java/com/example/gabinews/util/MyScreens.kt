package com.example.gabinews.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("homeScreen")
    object NewsScreen : MyScreens("newsScreen") {
        fun createRoute(category: String) = "newsScreen/$category"
    }
}