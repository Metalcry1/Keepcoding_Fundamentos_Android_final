package com.example.practica_fundamentos_android_v2.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica_fundamentos_android_v2.Heroes.HeroesActivity
import com.example.practica_fundamentos_android_v2.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {



    private val viewModel : LoginActivityViewModel by viewModels()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val emailField = binding.etEmail
        val passWordField = binding.etPassword
        val buttonLogin = binding.btLogin



        setObservers()
        binding.btLogin.setOnClickListener { viewModel.lauchLogin(emailField.text.toString(),passWordField.text.toString())

    }

    }
        private fun setObservers(){
            lifecycleScope.launch(Dispatchers.Main) {
              viewModel.uiState.collect{ state ->
                  when (state){
                      is LoginActivityViewModel.State.Idle -> idle()
                      is LoginActivityViewModel.State.Error -> showError()
                      is LoginActivityViewModel.State.Loading -> showLoading()
                      is LoginActivityViewModel.State.SucessLogin -> showSuccessLogin(state.token)

                      else -> {}
                  }
              }
            }

        }
    fun preferences(){

    }

    private fun showSuccessLogin(token: String) {
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val editablesPreferences = preferences.edit()
        editablesPreferences.putString("Token", token)
        editablesPreferences.apply()
        if (!token.isEmpty()) {
            Toast.makeText(binding.root.context, token, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HeroesActivity::class.java)
            startActivity(intent)
            binding.layoutLoadingInclude.root.visibility = View.GONE
        }else{
            Toast.makeText(binding.root.context, "No hay token", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading() {

        binding.layoutLoadingInclude.root.visibility = View.VISIBLE
    }

    private fun showError() {
        Toast.makeText(binding.root.context, "Error Login", Toast.LENGTH_SHORT).show()
    }

    private fun idle() {

    }

}