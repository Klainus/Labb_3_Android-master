package com.example.labb_3_android

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labb_3_android.cities.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _temperature = mutableStateOf("Loading...")
    val temperature: State<String> = _temperature

    private val _errorMessage = mutableStateOf("Loading...")
    val errorMessage: State<String> = _errorMessage

    private val _weatherIcon = mutableIntStateOf(0)
    val weatherIcon: State<Int> = _weatherIcon

    private val _weatherDescription = mutableStateOf("Loading...")
    val weatherDescription: State<String> = _weatherDescription


    private fun getIconResource(iconCode: String): Int {
        return when (iconCode) {
            "01d", "01n" -> R.drawable.clear_sky
            "02d", "02n" -> R.drawable.scattered_clouds
            "03d", "03n" -> R.drawable.scattered_clouds
            "04d", "04n" -> R.drawable.broken_clouds
            "09d", "09n" -> R.drawable.shower_rain
            "10d", "10n" -> R.drawable.rain
            "13d", "13n" -> R.drawable.snow
            else -> R.drawable.unknown_weather
        }
    }

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val result = weatherRepository.syncWeatherDataFromAPI(city, apiKey)

            result.onSuccess { weather ->
                _temperature.value = "Current temperature: ${weather.temperature}°C"
                _weatherDescription.value = "Description: ${weather.description}"
                _weatherIcon.value = getIconResource(weather.iconCode)
                _errorMessage.value = ""
            }

            result.onFailure { exception ->
                _errorMessage.value = "Failed to fetch data: ${exception.localizedMessage}"
                _temperature.value = ""
            }
        }
    }
}

