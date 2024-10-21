package com.example.weather20.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather20.WeatherRepository
import com.example.weather20.model.WeatherForecastResponse
import com.example.weather20.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData

    private val _forecast = MutableLiveData<WeatherForecastResponse?>()
    val forecast: LiveData<WeatherForecastResponse?> get() = _forecast

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _weatherData.value = null
            val weather = repository.getWeather(city)
            _weatherData.value = weather
            _isLoading.value = false
        }
    }

    fun fetchWeatherForecast(lat: Double, lon: Double) {
        viewModelScope.launch {
                val forecastResponse = repository.get7DayForecast(lat, lon)
                _forecast.value = forecastResponse
        }
    }



}

