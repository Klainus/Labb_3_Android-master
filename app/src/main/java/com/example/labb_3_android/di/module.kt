package com.example.labb_3_android.di

import android.app.Application
import android.content.Context
import com.example.labb_3_android.api.WeatherService
import com.example.labb_3_android.cities.WeatherDao
import com.example.labb_3_android.cities.WeatherDatabase
import com.example.labb_3_android.cities.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherDao: WeatherDao,
        weatherService: WeatherService,
    ): WeatherRepository {
        return WeatherRepository(weatherDao, weatherService)
    }
}
