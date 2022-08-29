package com.mahmutbulut.weatherapp.repository

import com.mahmutbulut.weatherapp.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService){

    suspend fun getWeather(cityName:String) = apiService.getWeather(cityName)

}