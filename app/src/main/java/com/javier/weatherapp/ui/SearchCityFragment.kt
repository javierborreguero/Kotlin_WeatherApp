package com.javier.weatherapp.ui

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.javier.weatherapp.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


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
        var city = editTextSearchCity.text.toString().trim()
        if (city == "") {
            Toast.makeText(requireContext(), "El campo no puede estar vacío", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), "Se buscará la ciudad", Toast.LENGTH_SHORT).show()
            tempUrl = "$apiUrl?q=$city&appid=$apiKey"
            // Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, tempUrl,
                // Si los datos están bien introducidos, mostramos el resultado que se obtiene al llamar a la API
                { response ->
                    Log.d("respuesta", response)
                    try {
                        var jsonResponse = JSONObject(response)
                        var jsonArray = jsonResponse.getJSONArray("weather")
                        var jsonObjectWeather = jsonArray.getJSONObject(0)
                      //  var description = jsonObjectWeather.getString("description")
                        var jsonObjectMain = jsonResponse.getJSONObject("main")
                        var temperature = jsonObjectMain.getDouble("temp") - 273.15
                        var cityName = jsonResponse.getString("name")
                        textViewResult.setTextColor(android.graphics.Color.rgb(68, 134, 199))
                        var outPut = ""
                        outPut += "\n Información actual de $cityName\n Temperatura: $temperature ºC"
                        textViewResult.text = outPut
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                // Si se produce algún error en la busqueda, se muestra el Toast indicando que ha habido un error
                {
                    Toast.makeText(
                        requireContext(),
                        "Se ha producido un error al hacer la búsqueda",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })
            // Add the request to the RequestQueue.
            val queue = Volley.newRequestQueue(requireContext())
            queue.add(stringRequest)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchCityFragment().apply {

            }
    }
}