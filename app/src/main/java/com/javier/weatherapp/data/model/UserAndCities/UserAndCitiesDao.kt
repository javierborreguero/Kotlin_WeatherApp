package com.javier.weatherapp.data.model.UserAndCities

import androidx.room.*

@Dao
interface UserAndCitiesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: UserAndCitiesCrossRef)

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getCities(userId: Int): Array<UserAndCities>
}