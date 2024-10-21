package com.example.weather20

import android.util.Log
import com.example.weather20.api.WeatherApiService
import com.example.weather20.model.WeatherForecastResponse
import com.example.weather20.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/"

    val api: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}

class WeatherRepository(private val api: WeatherApiService) {
    suspend fun getWeather(city: String): WeatherResponse? {
        return try {
            val response = api.getWeather(city, APPCONSTANT.AUTH_KEY)
            Log.e("TAG", "getWeather: $response", )
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun get7DayForecast(lat: Double, lon: Double): WeatherForecastResponse? {
        return try {
            val response = api.get7DayForecast(lat, lon, apiKey = APPCONSTANT.AUTH_KEY)
            Log.e("TAG", "get7DayForecast: ${response.body()}", )
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("TAG", "get7DayForecast: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

