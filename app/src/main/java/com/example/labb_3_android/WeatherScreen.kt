package com.example.labb_3_android

import android.health.connect.datatypes.units.Temperature
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    cityName: String,
    modifier: Modifier = Modifier
) {
    val temperature = viewModel.temperature.value
    val errorMessage = viewModel.errorMessage.value
    val weatherIcon = viewModel.weatherIcon.value
    val weatherDescription = viewModel.weatherDescription.value

    if (errorMessage.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if(errorMessage.isEmpty()) temperature else errorMessage,
                fontSize = 38.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage,
                fontSize = 18.sp
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weather in $cityName",
                fontSize = 38.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = temperature,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = weatherDescription,
                fontSize = 18.sp
            )
        }
    }
}
