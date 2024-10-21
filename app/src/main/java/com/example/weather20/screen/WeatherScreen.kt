package com.example.weather20.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.example.weather20.R
import com.example.weather20.component.ComposableLifecycle
import com.example.weather20.component.ForcastCard
import com.example.weather20.component.convertTimestampToDay
import com.example.weather20.model.Citylist
import com.example.weather20.viewModel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {

    val TAG = "WeatherScreen"
    val weather by viewModel.weatherData.observeAsState()
    val forcastData by viewModel.forecast.observeAsState()
    var searchText by rememberSaveable { mutableStateOf("Gurgaon") }
    var searchStart by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var hitApi by remember { mutableStateOf(false) }
    var forcastApi by remember { mutableStateOf(false) }
    var hitApi2 by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(false)


    LaunchedEffect(searchStart) {
        viewModel.fetchWeather(searchText)
        Log.e(TAG, "WeatherScreen: ${weather}", )
    }

//    ComposableLifecycle { owner, event ->
//        when (event) {
//            Lifecycle.Event.ON_START -> {
//                viewModel.fetchWeather(searchText)
//            }
//
//            else -> {
//
//            }
//        }
//    }

    val cities = listOf(
        Citylist("Varanasi"),
        Citylist("Haryana"),
        Citylist("Delhi"),
        Citylist("Noida"),
        Citylist("Jaipur")
    )

    val filteredCities = cities.filter {
        it.name!!.contains(searchText, ignoreCase = true)
    }

    Column(
        Modifier.fillMaxSize().background(colorResource(R.color.background_color)),
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                if (filteredCities.isNotEmpty()) {
                    expanded = true
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = {
                searchStart = !searchStart
                expanded = false
                hitApi = true
            }),
            placeholder = { Text(stringResource(R.string.select_state)) },
            modifier = Modifier.align(Alignment.Start).wrapContentHeight().statusBarsPadding()
                .padding(
                    dimensionResource(R.dimen.sixteen_dp)
                )
                .fillMaxWidth().border(
                    dimensionResource(R.dimen.one_dp),
                    colorResource(id = R.color.border_line_color),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.twenty_four_dp))
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedTrailingIconColor = Color.Gray
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Normal
            ),
            shape = RoundedCornerShape(dimensionResource(R.dimen.six_dp)),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.twenty_four_dp)),
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            }
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
        } else {
            if (expanded) {
                LazyColumn(
                    Modifier.padding(horizontal = dimensionResource(R.dimen.sixteen_dp))
                        .fillMaxHeight()
                ) {
                    items(filteredCities) { city ->
                        city.name?.let {
                            Text(
                                text = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        hitApi = true
                                        searchText = it
                                        searchStart = !searchStart
                                        expanded = false
                                    }
                                    .padding(dimensionResource(R.dimen.sixteen_dp))
                            )
                        }

                    }
                }
            }
            weather?.name?.let {
                Text(
                    it,
                    Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = dimensionResource(R.dimen.sixty_dp)),
                    style = TextStyle(fontSize = 30.sp)
                )
            }
            weather?.main?.temp?.let {
                Text(
                    "${it}°",
                    Modifier.align(Alignment.CenterHorizontally).padding(
                        top = dimensionResource(R.dimen.sixteen_dp),
                        bottom = dimensionResource(R.dimen.thirty_six)
                    ),
                    style = TextStyle(fontSize = 132.sp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .padding(horizontal = dimensionResource(R.dimen.fifty_dp)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weather?.main?.tempMax?.let {
                    Text(
                        "$it ↑",
                        Modifier.align(Alignment.CenterVertically).padding(),
                        style = TextStyle(fontSize = 25.sp)
                    )
                }
                weather?.main?.tempMin?.let {
                    Text(
                        "$it ↓",
                        Modifier.align(Alignment.CenterVertically).padding(),
                        style = TextStyle(fontSize = 25.sp)
                    )
                }

            }
            weather?.weather?.get(0)?.description?.let {
                Text(
                    it.toUpperCase(),
                    Modifier.align(Alignment.CenterHorizontally).padding(
                        top = dimensionResource(R.dimen.twenty_four_dp),
                        bottom = dimensionResource(R.dimen.thirty_six)
                    ),
                    style = TextStyle(fontSize = 18.sp)
                )
            }

//            if(/hitApi2)
//            {
                LazyRow(Modifier.padding(top = dimensionResource(R.dimen.thirty_six))) {
                    forcastData?.let {
                        items(it.daily) { dayForecast ->
                            val day = convertTimestampToDay(dayForecast.dt.toLong())
                            val maxTemp = Math.round((dayForecast.temp.max - 273.15) * 10.0) / 10.0
                            val minTemp = Math.round((dayForecast.temp.min - 273.15) * 10.0) / 10.0
                            ForcastCard(
                                day = day,
                                maxTemp = maxTemp,
                                minTemp = minTemp
                            )
                        }
                    }
//                }
            }

        }


    }


//    if (hitApi) {
        LaunchedEffect(weather){
            if(weather != null) {
                viewModel.fetchWeatherForecast(lat = weather!!.coord.lat, lon = weather!!.coord.lon)
                Log.e(TAG, "WeatherScreen: hit", )
            }
//        }
//        hitApi = false
//        when (weather?.responseCode) {
//            200 -> {
//                Log.e(TAG, "WeatherScreen: &&&&", )
//
//            }
//
//            else -> {
//                Toast.makeText(LocalContext.current, "Try again", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

//    if (forcastApi) {
//    if (forcastData != null){
//        hitApi2 = true
//    }
////        when(forcastData?.)
//        Log.e(TAG, "WeatherScreen:-- ${forcastData?.current}", )
//        Log.e(TAG, "WeatherScreen:-- ${forcastData?.daily}", )
//        Log.e(TAG, "WeatherScreen:-- ${forcastData?.daily?.size}", )
//
//
//        Log.e("TAG", "WeatherScreen: ${forcastData}")
//    }
}