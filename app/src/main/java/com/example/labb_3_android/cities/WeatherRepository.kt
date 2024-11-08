package com.example.labb_3_android.cities

import android.util.Log
import com.example.labb_3_android.api.WeatherResponse
import com.example.labb_3_android.api.WeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val coroutineScope: CoroutineScope
) {

    fun insertWeather(weather: Weather) {
        coroutineScope.launch(Dispatchers.IO) {
            weatherDao.insertWeather(weather)
        }
    }

    fun getAllWeather(): Flow<List<Weather>> {
        return weatherDao.getAllWeather()
    }

    fun getWeatherByDate(date: String): Flow<List<Weather>> {
        return weatherDao.getWeatherByDate(date)
    }

    fun performDatabaseOperation(dispatcher: CoroutineDispatcher, databaseOperation: suspend () -> Unit) {
        coroutineScope.launch(dispatcher) {
            databaseOperation()
        }
    }

    fun syncWeatherDataFromAPI(city: String, apiKey: String, weatherService: WeatherService) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val response = weatherService.getWeather(city, apiKey).execute()
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        val weather = Weather(
                            date = it.dt.toString(),
                            temperature = it.main.temp.toString(),
                            description = it.weather[0].description
                        )
                        insertWeather(weather)
                    }
                } else {
                    Log.e("WeatherRepository", "Failed to fetch weather: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("WeatherRepository", "Error syncing weather data", e)
            }
        }
    }
}
