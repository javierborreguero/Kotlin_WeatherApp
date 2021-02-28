package com.javier.weatherapp.data.model.city

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cityName: String,
    var cityTemperature: String
)