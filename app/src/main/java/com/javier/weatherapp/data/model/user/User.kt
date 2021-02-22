package com.javier.weatherapp.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val email: String,
    @PrimaryKey val userName: String,
    val password: String
)