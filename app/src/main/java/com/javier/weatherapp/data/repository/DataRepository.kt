package com.javier.weatherapp.data.repository

import android.content.Context
import com.javier.weatherapp.data.database.AppDatabase
import com.javier.weatherapp.data.model.User
import com.javier.weatherapp.data.model.UserDao
import kotlinx.coroutines.*

class DataRepository(context: Context) {
    private val userDao: UserDao? = AppDatabase.getInstance(context)?.userDao()
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
}