package com.example.weather20.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@Composable
fun WeatherIcon(icon: String) {
    val iconUrl = "http://openweathermap.org/img/wn/${icon}@2x.png"

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(iconUrl)
            .crossfade(true)
            .build(),
        loading = {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        },
        error = {
            Text(text = "Error loading image")
        },
        contentDescription = "Weather Icon",
        modifier = Modifier.size(64.dp)
    )
}