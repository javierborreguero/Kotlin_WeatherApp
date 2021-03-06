package com.javier.weatherapp.data.model.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0,
    @ColumnInfo(name = "cityName") var cityName: String,
    @ColumnInfo(name = "cityTemperature") var cityTemperature: String,
    @ColumnInfo(name = "cityPressure") var cityPressure: String,
    @ColumnInfo(name = "cityHumidity") var cityHumidity: String
)