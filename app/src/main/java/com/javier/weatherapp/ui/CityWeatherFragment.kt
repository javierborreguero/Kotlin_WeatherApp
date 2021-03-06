package com.javier.weatherapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.javier.weatherapp.R
import com.javier.weatherapp.adapters.CityAdapter
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.data.repository.DataRepository
import com.javier.weatherapp.viewModel.CityViewModel
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat

class CityWeatherFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var fab: FloatingActionButton
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
        recyclerView = v.findViewById(R.id.recyclerView)
        fab = v.findViewById(R.id.fab)
        showCities()
        fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_cityWeatherFragment_to_addCityFragment)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        var menuItemSearch = menu.findItem(R.id.app_bar_search)
        menuItemSearch.isVisible = true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutFragment -> {
                val preferences = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = preferences!!.edit()
                editor.putString("login", "false")
                editor.apply()
                Toast.makeText(context, "Adios", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_cityWeatherFragment_to_homeFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityWeatherFragment().apply {
            }
    }
}