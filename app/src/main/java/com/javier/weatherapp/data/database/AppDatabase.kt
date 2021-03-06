package com.javier.weatherapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.javier.weatherapp.data.model.UserAndCities.UserAndCitiesCrossRef
import com.javier.weatherapp.data.model.UserAndCities.UserAndCitiesDao
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.model.city.CityDao
import com.javier.weatherapp.data.model.user.User
import com.javier.weatherapp.data.model.user.UserDao

@Database(entities = [User::class, City::class, UserAndCitiesCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao
    abstract fun userAndCitiesDao(): UserAndCitiesDao

    companion object {
        private const val DATABASE_NAME = "cities_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME,
                ).build()
            }
            return INSTANCE
        }
    }
}