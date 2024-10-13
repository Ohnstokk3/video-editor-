package com.example.video_editor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.video_editor.MainScreen.SimpleMediaScreen
import com.example.video_editor.nav.Destination
import com.example.video_editor.service.SimipleMediaService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SimpleMediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Destination.Main.route) {
                composable(Destination.Main.route) {
                    SimpleMediaScreen(
                        vm = viewModel,
                    )
                }

            }
        }

    }

}

