package com.javier.weatherapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.javier.weatherapp.R
import com.javier.weatherapp.adapters.CityAdapter
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.repository.DataRepository
import com.javier.weatherapp.viewModel.CityViewModel
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat

class CityWeatherFragment : Fragment() {
    private lateinit var editTextTextAddCity: EditText
    private lateinit var buttonAdd: Button
    private lateinit var textViewName: TextView
    private lateinit var textViewTemperature: TextView
    private var cityId = 0
    private val apiUrl = "http://api.openweathermap.org/data/2.5/weather"
    private val apiKey = "9f249e3ba99c564ea61245c238f2831c"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var showCity: City
    var activityListener: View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_city_weather, container, false)
        textViewName = v.findViewById(R.id.textViewName)
        textViewTemperature = v.findViewById(R.id.textViewTemperature)
        editTextTextAddCity = v.findViewById(R.id.editTextTextAddCity)
        buttonAdd = v.findViewById(R.id.buttonAdd)
        recyclerView = v.findViewById(R.id.recyclerView)
        showCities()
        buttonAdd.setOnClickListener {
            getWeatherDetails()
        }
        return v
    }

    private fun showCities() {
        var dataRepository = DataRepository(requireContext())
        var city = dataRepository.getCity()
        var viewModel = ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
        adapter = CityAdapter(city, viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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
                        if (dataRepository.insertCity(
                                City(
                                    cityId,
                                    editTextTextAddCity.text.toString(),
                                    textViewTemperature.text.toString()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        var menuItemSearch = menu.findItem(R.id.app_bar_search)
        menuItemSearch.setVisible(true)
        var searchView = menuItemSearch.actionView as SearchView
        searchView.queryHint = "Buscar"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // adapter.filter.filter(newText)
                return false
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityWeatherFragment().apply {
            }
    }
}