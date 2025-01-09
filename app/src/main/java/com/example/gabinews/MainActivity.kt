package com.example.gabinews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gabinews.di.appModule
import com.example.gabinews.ui.feature.detail.DetailScreen
import com.example.gabinews.ui.feature.home.HomeScreen
import com.example.gabinews.ui.feature.news.NewsScreen
import com.example.gabinews.ui.theme.AppTheme
import com.example.gabinews.util.Constants.KEY_ARTICLE_ARG
import com.example.gabinews.util.Constants.KEY_CATEGORY_ARG
import com.example.gabinews.util.MyScreens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(appModule)
            }) {
                AppTheme {
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = !isSystemInDarkTheme()

                    DisposableEffect(systemUiController, useDarkIcons) {
                        systemUiController.setSystemBarsColor(
                            color = Color.Transparent,
                            darkIcons = useDarkIcons
                        )
                        onDispose {}
                    }

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        GabiNewsApp()
                    }
                }
            }
        }
    }
}

@Composable
fun GabiNewsApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = MyScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = MyScreens.NewsScreen.route + "/{$KEY_CATEGORY_ARG}",
            arguments = listOf(
                navArgument(KEY_CATEGORY_ARG) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            NewsScreen(
                category = backStackEntry.arguments?.getString(KEY_CATEGORY_ARG) ?: "",
                navController = navController
            )
        }

        composable(
            route = MyScreens.DetailScreen.route + "/{$KEY_ARTICLE_ARG}",
            arguments = listOf(navArgument(KEY_ARTICLE_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString(KEY_ARTICLE_ARG) ?: ""
            DetailScreen(articleJson = articleJson, navController = navController)
        }

    }
}
