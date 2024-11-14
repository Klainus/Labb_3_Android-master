package com.example.labb_3_android.cities

import android.util.Log
import com.example.labb_3_android.api.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
) {

    suspend fun insertWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            weatherDao.insertWeather(weather)
        }
    }

    fun getAllWeather(): Flow<List<Weather>> {
        return weatherDao.getAllWeather()
    }

    suspend fun syncWeatherDataFromAPI(city: String, apiKey: String): Result<Weather> {
        return try {
            val weatherResponse = weatherService.getWeather(city, apiKey)



            weatherResponse.let {
                val weather = Weather(
                    date = System.currentTimeMillis().toString(),
                    temperature = it.main.temp.toString(),
                    description = it.weather[0].description,
                    iconCode = it.weather[0].icon
                )
                Result.success(weather)
            }

        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather", e)
            Result.failure(e)
        }
    }
}






