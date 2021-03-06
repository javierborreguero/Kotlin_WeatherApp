package com.javier.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.javier.weatherapp.R
import com.javier.weatherapp.data.model.city.City
import com.javier.weatherapp.viewModel.CityViewModel

class CityAdapter(var cities: List<City>, private var cityViewModel: CityViewModel) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    var cityFilterList = ArrayList<City>()

    init {
        cityFilterList = cities as ArrayList<City>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        holder.bindItems(cityFilterList[position])
        holder.itemView.setOnClickListener {
            cityViewModel.setSelectedCity(cityFilterList[position])
            it.findNavController().navigate(R.id.action_cityWeatherFragment_to_manageCityFragment)
        }
    }

    override fun getItemCount(): Int {
        return cityFilterList.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSequence = constraint.toString()
                cityFilterList = if (charSequence.isEmpty()) {
                    cities as ArrayList<City>
                } else {
                    val resultList = ArrayList<City>()
                    for (row in cities) {
                        if (row.cityName?.toLowerCase()
                                ?.contains(charSequence.toLowerCase())!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                var filterResult = FilterResults()
                filterResult.values = cityFilterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cityFilterList = results?.values as ArrayList<City>
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cities: City) {
            val textViewCityName = itemView.findViewById<TextView>(R.id.textViewCityName)
            textViewCityName.text = cities.cityName + ":"
            val textViewCityTemperature = itemView.findViewById<TextView>(R.id.textViewCityTemperatrue)
            textViewCityTemperature.text = "Temp " + cities.cityTemperature + " ºC"
            val textViewCityPressure = itemView.findViewById<TextView>(R.id.textViewCityPresure)
            textViewCityPressure.text = "Pres " + cities.cityPressure + " (atm)"
            val textViewCityHumidity = itemView.findViewById<TextView>(R.id.textViewCityHumidity)
            textViewCityHumidity.text = "Hum " + cities.cityHumidity + " %"
        }

    }
}