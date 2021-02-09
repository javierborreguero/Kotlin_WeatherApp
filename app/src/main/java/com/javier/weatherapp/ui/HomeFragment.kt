package com.javier.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.javier.weatherapp.R
import com.javier.weatherapp.databse.controller.DataRepository


class HomeFragment : Fragment() {
    private lateinit var editTextLoginUserName: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        editTextLoginUserName = v.findViewById(R.id.editTextLoginUserName)
        editTextLoginPassword = v.findViewById(R.id.editTextLoginPassword)
        buttonLogin = v.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            userLogin()
        }
        buttonRegister = v.findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }
        return v
    }

    private fun userLogin() {
        val dataRepository = DataRepository(requireContext())
        if (dataRepository.selectUser() == 0) {
            val action = HomeFragmentDirections.actionHomeFragmentToRegisterFragment()
            findNavController().navigate(action)
        } else {
            if (dataRepository.userIsLogin(
                    editTextLoginUserName.text.toString(),
                    editTextLoginPassword.text.toString()
                )
            ) {
                val action = HomeFragmentDirections.actionHomeFragmentToCityWeatherFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Datos incorrectos", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}