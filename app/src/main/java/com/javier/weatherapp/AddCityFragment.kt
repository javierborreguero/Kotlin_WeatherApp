package com.javier.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.javier.weatherapp.data.repository.DataRepository
import com.android.volley.Request
import org.json.JSONObject
import java.text.NumberFormat
import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.android.volley.toolbox.Volley
import com.javier.weatherapp.data.model.city.City
import org.json.JSONException

class AddCityFragment : Fragment() {
    private lateinit var editTextTextAddCity: EditText
    private lateinit var buttonAdd: Button
    private var cityId = 0
    private val apiUrl = "http://api.openweathermap.org/data/2.5/weather"
    private val apiKey = "9f249e3ba99c564ea61245c238f2831c"
    private lateinit var textViewName: TextView
    private lateinit var textViewTemperature: TextView
    private lateinit var textViewPressure: TextView
    private lateinit var textViewHumidity: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_add_city, container, false)
        editTextTextAddCity = v.findViewById(R.id.editTextTextAddCity)
        textViewName = v.findViewById(R.id.textViewName)
        textViewTemperature = v.findViewById(R.id.textViewTemperature)
        textViewPressure = v.findViewById(R.id.textViewPressure)
        textViewHumidity = v.findViewById(R.id.textViewHumidity)
        buttonAdd = v.findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            getWeatherDetails()
            findNavController().navigate(R.id.action_addCityFragment_to_cityWeatherFragment)
        }
        return v

    }

    private fun getWeatherDetails() {
        val dataRepository = DataRepository(requireContext())
        var tempUrl: String = ""
        var city = editTextTextAddCity.text.toString().trim()
        if (city == "") {
            Toast.makeText(requireContext(), "El campo no puede estar vacío", Toast.LENGTH_SHORT)
                .show()
        } else {
            tempUrl = "$apiUrl?q=$city&appid=$apiKey"
            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, tempUrl,
                // Si los datos están bien introducidos, mostramos el resultado que se obtiene al llamar a la API
                { response ->
                    Log.d("respuesta", response)
                    try {
                        var jsonResponse = JSONObject(response)
                        var jsonObjectMain = jsonResponse.getJSONObject("main")
                        var temperature = jsonObjectMain.getDouble("temp") - 273.15
                        var cityName = jsonResponse.getString("name")
                        var cityPressure = jsonObjectMain.getInt("pressure")
                        var cityHumidity = jsonObjectMain.getInt("humidity")
                        textViewName.setTextColor(Color.rgb(68, 134, 199))
                        textViewName.text = cityName
                        textViewTemperature.setTextColor(
                            Color.rgb(
                                68,
                                134,
                                199
                            )
                        )
                        val tempToString: NumberFormat = NumberFormat.getNumberInstance()
                        textViewTemperature.text = (tempToString.format(temperature))
                        textViewPressure.setTextColor(
                            Color.rgb(
                                68,
                                134,
                                199
                            )
                        )
                        var pressToString: NumberFormat = NumberFormat.getNumberInstance()
                        textViewPressure.text = (pressToString.format(cityPressure))
                        textViewHumidity.setTextColor(
                            Color.rgb(
                                68,
                                134,
                                199
                            )
                        )
                        var humToString: NumberFormat = NumberFormat.getNumberInstance()
                        textViewHumidity.text = (humToString.format(cityHumidity))
                        if (dataRepository.insertCity(
                                City(
                                    cityId,
                                    editTextTextAddCity.text.toString(),
                                    textViewTemperature.text.toString(),
                                    textViewPressure.text.toString(),
                                    textViewHumidity.text.toString()
                                )
                            ) == -1
                        ) {
                            Toast.makeText(
                                requireContext(),
                                "La ciudad ha sido registrado",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
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
            AddCityFragment().apply {

            }
    }
}