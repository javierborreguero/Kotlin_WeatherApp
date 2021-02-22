package com.javier.weatherapp.data.repository

import android.content.Context
import android.os.AsyncTask
import com.javier.weatherapp.data.database.AppDatabase
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.model.city.CityDao
import com.javier.weatherapp.data.model.user.User
import com.javier.weatherapp.data.model.user.UserDao
import kotlinx.coroutines.*

class DataRepository(context: Context) {
    private val userDao: UserDao? = AppDatabase.getInstance(context)?.userDao()
    private val cityDao: CityDao? = AppDatabase.getInstance(context)?.cityDao()
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

    fun insertCity(city: City): Int {
        if (cityDao != null) {
            CoroutineScope(Dispatchers.IO).launch {
                cityDao.insert(city)
            }
            return 0
        }
        return -1
    }


    fun selectCity(): Int = runBlocking {
        cityDao!!.selectCity()!!
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
}