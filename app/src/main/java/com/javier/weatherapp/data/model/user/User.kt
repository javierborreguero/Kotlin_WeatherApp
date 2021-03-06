package com.javier.weatherapp.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    @ColumnInfo(name = "email")  val email: String,
    @ColumnInfo(name = "userName")  val userName: String,
    @ColumnInfo(name = "password")  val password: String
)