package com.javier.weatherapp.data.repository

import android.content.Context
import android.os.AsyncTask
import com.javier.weatherapp.data.database.AppDatabase
import com.javier.weatherapp.data.model.UserAndCities.UserAndCities
import com.javier.weatherapp.data.model.UserAndCities.UserAndCitiesCrossRef
import com.javier.weatherapp.data.model.UserAndCities.UserAndCitiesDao
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.model.city.CityDao
import com.javier.weatherapp.data.model.user.User
import com.javier.weatherapp.data.model.user.UserDao
import kotlinx.coroutines.*

class DataRepository(context: Context) {
    private val userDao: UserDao? = AppDatabase.getInstance(context)?.userDao()
    private val cityDao: CityDao? = AppDatabase.getInstance(context)?.cityDao()
    private val userAndCities: UserAndCitiesDao? =
        AppDatabase.getInstance(context)?.userAndCitiesDao()

    fun insertUser(user: User): Int {
        if (userDao != null) {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insert(user)
            }
            return 0
        }
        return -1
    }

    fun selectUser(): Int = runBlocking {
        userDao!!.selectUser()!!
    }

    fun userIsLogin(userName: String, password: String): Boolean {
        var job: Job
        job = CoroutineScope(Dispatchers.IO).async {
            userDao!!.selectUserByUserNameAndPassword(userName, password)
        }
        return runBlocking {
            job.await() == 1
        }
    }

    // Ciudades
    fun getCity(): List<City> = runBlocking {
        cityDao!!.getCity()
    }
    fun insertCity(city: City): Int {
        if (cityDao != null) {
            CoroutineScope(Dispatchers.IO).launch {
                cityDao.insert(city)
            }
            return 0
        }
        return -1
    }

    fun deleteCity(cityName: String): Int {
        if (cityDao != null) {
            CoroutineScope(Dispatchers.IO).launch {
                cityDao.delete(cityName)
            }
            return 0
        }
        return -1
    }

    fun updateCity(mName: String, newCityName: String): Int {
        if (cityDao != null) {
            CoroutineScope(Dispatchers.IO).launch {
                cityDao.update(mName, newCityName)
            }
            return 0
        }
        return -1
    }


    // Relación usuario-ciudades

//    private class InsertUserAndCitiesAsyncTask(
//        private val userDao: UserDao,
//        private val cityDao: CityDao,
//        private val userAndCitiesDao: UserAndCitiesDao
//    ) : AsyncTask<UserAndCities, Void, Int>() {
//        override fun doInBackground(vararg userAndCities: UserAndCities?): Int {
//            try {
//                for (userAndCities in userAndCities) {
//                    if (userAndCities != null) {
//                        userDao.insertAll(userAndCities.user)
//                        cityDao.insertAll(userAndCities.cities)
//                        for (cities in userAndCities.cities) {
//                            userAndCitiesDao.insert(
//                                UserAndCitiesCrossRef(
//                                    userAndCities.user.userId,
//                                    cities.cityId
//                                )
//                            )
//                        }
//                    }
//                }
//                return 0
//            } catch (exception: Exception) {
//                return -1
//            }
//        }
//    }

}