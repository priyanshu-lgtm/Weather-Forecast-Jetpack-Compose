package com.example.weather20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.weather20.screen.WeatherScreen
import com.example.weather20.viewModel.WeatherViewModel
import com.example.weather20.viewModel.WeatherViewModelProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = WeatherRepository(RetrofitInstance.api)
        val viewModel: WeatherViewModel = ViewModelProvider(
            this,
            WeatherViewModelProvider(repository)
        ).get(WeatherViewModel::class.java)
        setContent {
            WeatherScreen(viewModel)
        }
    }
}

