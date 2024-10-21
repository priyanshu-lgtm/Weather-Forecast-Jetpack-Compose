package com.example.weather20.component

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertTimestampToDay(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val date = Date(timestamp * 1000)  // Convert seconds to milliseconds
    return sdf.format(date)
}
