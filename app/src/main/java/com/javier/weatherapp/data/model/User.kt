package com.javier.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val userName: String,
    val email: String,
    val password: String
)