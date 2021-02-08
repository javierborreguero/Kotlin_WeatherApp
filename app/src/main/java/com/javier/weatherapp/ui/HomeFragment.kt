package com.javier.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.javier.weatherapp.R


class HomeFragment : Fragment() {
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
        buttonLogin = v.findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            Toast.makeText(requireContext(), "Iniciar sesión", Toast.LENGTH_LONG).show()
        }
        buttonRegister = v.findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}