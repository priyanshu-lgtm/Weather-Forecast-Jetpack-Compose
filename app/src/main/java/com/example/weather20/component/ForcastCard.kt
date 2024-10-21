package com.example.weather20.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather20.R

@Composable
fun ForcastCard(day: String, maxTemp: Double, minTemp: Double) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp).background(colorResource(R.color.background_color1)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.background_color1),
        ),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.eight_dp)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = day, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally))  // Day text
            Text(text = "Max: ${maxTemp}°C", fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(top = dimensionResource(R.dimen.sixteen_dp)).align(Alignment.CenterHorizontally))
            Text(text = "Min: ${minTemp}°C", fontSize = 14.sp, color = Color.Gray,modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
