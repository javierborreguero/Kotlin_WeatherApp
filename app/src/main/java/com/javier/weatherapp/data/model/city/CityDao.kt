package com.javier.weatherapp.data.model.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT count(*) FROM city where cityName = :cityName and cityTemperature = :cityTemperature")
    suspend fun selectUserByCityNameAndTemperature(cityName: String, cityTemperature: Double): Int?

    @Query("SELECT count(*) FROM city")
    suspend fun selectCity(): Int?

    @Insert
    suspend fun insert(city: City)
}