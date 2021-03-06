package com.javier.weatherapp.data.model.UserAndCities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.model.user.User

data class UserAndCities(
    @Embedded
    var user: User,
    @Relation(
        parentColumn = "userId",
        entity = City::class,
        entityColumn = "cityId",
        associateBy = Junction(
            value = UserAndCitiesCrossRef::class,
            parentColumn = "userId",
            entityColumn = "cityId"
        )
    )
    var cities: List<City>
)

@Entity(primaryKeys = ["userId", "cityId"])
data class UserAndCitiesCrossRef(
    val userId: Int,
    val cityId: Int
)