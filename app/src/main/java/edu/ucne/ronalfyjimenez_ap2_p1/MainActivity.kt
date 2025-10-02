package edu.ucne.ronalfyjimenez_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.ronalfyjimenez_ap2_p1.presentation.navigation.HuacalesNavHost
import edu.ucne.ronalfyjimenez_ap2_p1.ui.theme.RonalfyJimenez_AP2_P1Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RonalfyJimenez_AP2_P1Theme {
                val navController = rememberNavController()
                HuacalesNavHost(nav = navController)
            }
        }
    }
}