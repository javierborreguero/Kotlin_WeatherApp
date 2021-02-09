package com.javier.weatherapp.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.javier.weatherapp.R

class CityWeatherFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_city_weather, container, false)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        var menuItemSearch = menu.findItem(R.id.app_bar_search)
        menuItemSearch.setVisible(true)
        var searchView = menuItemSearch.actionView as SearchView
        searchView.queryHint = "Buscar"
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityWeatherFragment().apply {
            }
    }
}