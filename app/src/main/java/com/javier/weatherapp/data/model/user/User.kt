package com.javier.weatherapp.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val email: String,
    val userName: String,
    val password: String
)