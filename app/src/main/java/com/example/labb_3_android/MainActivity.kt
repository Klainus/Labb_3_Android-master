package com.example.labb_3_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.labb_3_android.ui.theme.Labb_3_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Labb_3_AndroidTheme {

                LaunchedEffect(Unit) {
                    weatherViewModel.fetchWeather("Stockholm", "c9097b15b320ddb685ada35f541bf654")
                }

                val temperature by weatherViewModel.temperature
                val errorMessage by weatherViewModel.errorMessage
                val weatherDescription by weatherViewModel.weatherDescription
                val weatherIcon by weatherViewModel.weatherIcon

                val iconResource = if (errorMessage.isEmpty()) weatherIcon else R.drawable.unknown_weather

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherScreen(
                        cityName = "Stockholm",
                        viewModel = weatherViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                }
            }
        }
    }

