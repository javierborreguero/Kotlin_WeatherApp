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
import com.javier.weatherapp.data.controller.DataRepository
import com.javier.weatherapp.data.model.User

class RegisterFragment : Fragment() {
    lateinit var editTextUserEmail: EditText
    lateinit var editTextUserName: EditText
    lateinit var editTextPassword: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonAccept: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        editTextUserEmail = v.findViewById(R.id.editTextUserEmail)
        editTextUserName = v.findViewById(R.id.editTextUserName)
        editTextPassword = v.findViewById(R.id.editTextPassword)
        buttonAccept = v.findViewById(R.id.buttonAccept)
        buttonAccept.setOnClickListener {
            registerNewUser()
        }
        buttonCancel = v.findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
        }
        return v
    }

    private fun registerNewUser() {
        val dataRepository = DataRepository(requireContext())
        if (dataRepository.insertUser(
                User(
                    editTextUserEmail.text.toString(),
                    editTextUserName.text.toString(),
                    editTextPassword.text.toString()
                )
            ) == -1
        ) {
            Toast.makeText(requireContext(), "El usuario ya ha sido registrado", Toast.LENGTH_LONG)
                .show()
        } else {
            if (editTextUserEmail.text.toString() == "" || editTextUserName.text.toString() == "" || editTextPassword.text.toString() == "") {
                Toast.makeText(requireContext(), "Por favor rellene todos los campos para poder finalizar el registro", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Usuario registrado con exito", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_registerFragment_to_cityWeatherFragment)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
            }
    }
}