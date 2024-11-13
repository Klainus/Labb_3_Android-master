package com.example.labb_3_android.cities

import android.util.Log
import com.example.labb_3_android.api.WeatherResponse
import com.example.labb_3_android.api.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
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
            val response: Response<WeatherResponse> = withContext(Dispatchers.IO) {
                weatherService.getWeather(city, apiKey).execute()
            }

            if (response.isSuccessful) {
                val weatherResponse = response.body()
                Log.d("WeatherRepository", "Response body: $weatherResponse")
                weatherResponse?.let {
                    val weather = Weather(
                        date = System.currentTimeMillis().toString(),
                        temperature = it.main.temp.toString(),
                        description = it.weather[0].description,
                        iconCode = it.weather[0].icon
                    )
                    Result.success(weather)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Log.e("WeatherRepository", "API call failed with code: ${response.code()}")
                Result.failure(Exception("Failed to fetch weather: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather", e)
            Result.failure(e)
        }
    }
}






