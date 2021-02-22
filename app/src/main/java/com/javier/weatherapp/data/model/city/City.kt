package com.javier.weatherapp.data.model.city

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
   @PrimaryKey val cityName: String,
   val cityTemperature: Double
)