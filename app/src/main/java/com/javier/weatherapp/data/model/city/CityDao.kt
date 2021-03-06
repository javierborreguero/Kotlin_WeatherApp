package com.javier.weatherapp.data.model.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    suspend fun getCity(): List<City>


    @Query("SELECT * FROM city where cityId = :id")
    suspend fun getCityById(id: Int): City

    @Insert
    suspend fun insert(city: City)

    @Insert
    suspend fun insert(cities: List<City>)


    @Query("DELETE FROM city WHERE cityName = :cityName")
    suspend fun delete(cityName: String)

    @Query("UPDATE city SET cityName = :newCityName Where cityName = :mName")
    suspend fun update(mName: String, newCityName: String): Int?
}