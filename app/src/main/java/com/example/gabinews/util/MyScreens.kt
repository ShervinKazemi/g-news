package com.example.gabinews.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("home")
    object NewsScreen : MyScreens("news") {
        fun createRoute(category: String) = "$route/$category"
    }
    object DetailScreen : MyScreens("detail") {
        fun createRoute(articleJson: String) = "$route/$articleJson"
    }
}