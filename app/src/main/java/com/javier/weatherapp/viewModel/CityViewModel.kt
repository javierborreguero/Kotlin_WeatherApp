package com.javier.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import com.javier.weatherapp.data.model.city.City

class CityViewModel : ViewModel() {
    private var selectedCity: City

    init {
        selectedCity = City(0, "", "", "", "")
    }

    fun getSelectedCity(): City {
        return selectedCity
    }

    fun setSelectedCity(city: City) {
        selectedCity = city
    }
}