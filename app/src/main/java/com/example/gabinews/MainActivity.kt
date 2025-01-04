package com.example.gabinews

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gabinews.di.appModule
import com.example.gabinews.ui.theme.AppTheme
import com.example.gabinews.util.MyScreens
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
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                    ) { paddingValues ->
                        GabiNewsApp(modifier = Modifier.padding(paddingValues))
                    }
                }
            }
        }
    }
}

@Composable
fun GabiNewsApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    KoinNavHost(navController = navController, startDestination = MyScreens.HomeScreen.route) {

        composable(route = MyScreens.HomeScreen.route) {
            HomeScreen()
        }

    }
}
