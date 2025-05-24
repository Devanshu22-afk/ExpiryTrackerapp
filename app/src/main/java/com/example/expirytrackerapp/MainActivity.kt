package com.example.expirytrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expirytrackerapp.ui.screens.AddItemScreen
import com.example.expirytrackerapp.ui.screens.HomeScreen
import com.example.expirytrackerapp.ui.screens.ViewItemsScreen
import com.example.expirytrackerapp.ui.theme.ExpiryTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpiryTrackerAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpiryTrackerApp()
                }
            }
        }
    }
}

@Composable
fun ExpiryTrackerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
}
        composable("add_item") {
            AddItemScreen(navController = navController)
        }
        composable("view_items") {
            ViewItemsScreen(navController = navController)
        }
    }
}