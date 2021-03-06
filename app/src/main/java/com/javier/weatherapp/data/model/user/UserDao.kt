package com.javier.weatherapp.data.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.javier.weatherapp.data.model.user.User

@Dao
interface UserDao {
    @Query("SELECT count(*) FROM user where userName = :userName and password = :password")
    suspend fun selectUserByUserNameAndPassword(userName: String, password: String): Int?

    @Query("SELECT count(*) FROM user")
    suspend fun selectUser(): Int?

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserOne(userId: Int): Array<User>

    @Insert
    suspend fun insert(user: User)

    @Insert
    fun insertAll(users: List<User>)

    @Insert
    fun insertAll(vararg users: User)


}