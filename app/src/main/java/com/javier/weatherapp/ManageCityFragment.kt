package com.javier.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.javier.weatherapp.data.repository.DataRepository
import com.javier.weatherapp.viewModel.CityViewModel

class ManageCityFragment : Fragment() {
    private lateinit var editTextName: EditText
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_manage_city, container, false)
        editTextName = v.findViewById(R.id.editTextName)
        buttonUpdate = v.findViewById(R.id.buttonUpdate)
        buttonDelete = v.findViewById(R.id.buttonDelete)
        var dataRepository = DataRepository(requireContext())
        var viewModel = ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
        var city = viewModel.getSelectedCity()
        editTextName.setText(city.cityName)
        buttonUpdate.setOnClickListener {
            city.cityName.let { cityNameToUpdate ->
                dataRepository.updateCity(cityNameToUpdate, editTextName.text.toString())
                val updateRight = false
                if (!updateRight) {
                    Toast.makeText(context, "Ciudad actualizada con exito", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_manageCityFragment_to_cityWeatherFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Se ha producido un error al actualizar la ciudad",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        buttonDelete.setOnClickListener {
            city.cityName.let { cityNameToDelete ->
                dataRepository.deleteCity(cityNameToDelete)
                val deleteRight = false
                if (!deleteRight) {
                    Toast.makeText(context, "Ciudad eliminada con exito", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_manageCityFragment_to_cityWeatherFragment)
                } else {
                    Toast.makeText(
                        context,
                        "Se ha producido un error al eliminar la ciudad",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return v
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManageCityFragment().apply {
            }
    }
}