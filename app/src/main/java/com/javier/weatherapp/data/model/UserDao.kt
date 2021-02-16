package com.javier.weatherapp.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT count(*) FROM user where userName = :userName and password = :password")
    suspend fun selectUserByUserNameAndPassword(userName:String, password: String): Int?

    @Query("SELECT count(*) FROM user")
    suspend fun selectUser(): Int?

    @Insert
    suspend fun insert(user: User)
}