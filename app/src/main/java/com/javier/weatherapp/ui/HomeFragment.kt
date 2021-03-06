package com.javier.weatherapp.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.javier.weatherapp.R
import com.javier.weatherapp.data.repository.DataRepository


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
        findNavController().navigate(R.id.action_homeFragment_to_cityWeatherFragment)
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
                if (editTextLoginUserName.text.toString() == "" ||
                    editTextLoginPassword.text.toString() == ""
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Por favor rellene todos los campos para poder identificar el usuario",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val preferences = activity?.getSharedPreferences("login", Context.MODE_PRIVATE)
                    var editor: SharedPreferences.Editor = preferences!!.edit()
                    editor.putString("login", "true")
                    editor.apply()
                    val preferencesUsr =
                        activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
                    var editorUsr: SharedPreferences.Editor = preferencesUsr!!.edit()
                    editorUsr.putString("user", editTextLoginUserName.text.toString())
                    editorUsr.apply()
                    //  val action = HomeFragmentDirections.actionHomeFragmentToCityWeatherFragment()
                    findNavController().navigate(R.id.action_homeFragment_to_cityWeatherFragment)
                }
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