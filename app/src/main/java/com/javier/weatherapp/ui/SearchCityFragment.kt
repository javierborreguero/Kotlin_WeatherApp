package com.javier.weatherapp.ui

import android.app.DownloadManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.javier.weatherapp.R


class SearchCityFragment : Fragment() {
    private lateinit var editTextSearchCity: EditText
    private lateinit var textViewResult: TextView
    private lateinit var buttonSearch: Button
    private val apiUrl = "http://api.openweathermap.org/data/2.5/weather"
    private val apiKey = "9f249e3ba99c564ea61245c238f2831c"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_search_city, container, false)
        editTextSearchCity = v.findViewById(R.id.editTextSearchCity)
        textViewResult = v.findViewById(R.id.textViewResult)
        buttonSearch = v.findViewById(R.id.buttonSearch)
        buttonSearch.setOnClickListener {
            getWeatherDetails()
        }
        return v
    }

    private fun getWeatherDetails() {
        var tempUrl: String = ""
        var city = editTextSearchCity.text.toString()
        if (city == "") {
            Toast.makeText(requireContext(), "El campo no puede estar vacío", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), "Se buscará la ciudad", Toast.LENGTH_SHORT).show()
            tempUrl = "$apiUrl?q=$city&appid=$apiKey"
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchCityFragment().apply {

            }
    }
}