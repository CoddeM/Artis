package com.artis.artis.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.artis.artis.presentation.drawing.DrawingScreen // Import DrawingScreen
//import com.artis.artis.presentation.navigation.AppNavHost // If you implement navigation
import com.artis.artis.ui.theme.ArtisTheme

@Composable
fun ArtisApp() {
    ArtisTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            // If you have multiple screens, use a NavHost here
            // val navController = rememberNavController()
            // AppNavHost(navController = navController)
            DrawingScreen() // For now, directly show DrawingScreen
        }
    }
}