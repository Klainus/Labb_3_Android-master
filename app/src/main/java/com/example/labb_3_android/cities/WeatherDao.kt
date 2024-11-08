package com.example.labb_3_android.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert
    suspend fun insertWeather(weather: Weather)

    @Query("SELECT * FROM weather")
    fun getAllWeather(): Flow<List<Weather>>

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date: String): Flow<List<Weather>>

    @Query("DELETE FROM weather WHERE id = :id")
    suspend fun deleteWeatherById(id: Long)
}
