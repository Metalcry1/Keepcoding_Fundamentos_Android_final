package com.example.practica_fundamentos_android_v2.UI.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica_fundamentos_android_v2.UI.Heroes.HeroesActivity
import com.example.practica_fundamentos_android_v2.databinding.ActivityLoginBinding
import com.example.practica_fundamentos_android_v2.Data.Database.PreferenceAplication.Companion.prefRepository
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

        setObservers()
        binding.btLogin.setOnClickListener { viewModel.lauchLogin(emailField.text.toString(),passWordField.text.toString()) }

    }
        private fun setObservers(){
            lifecycleScope.launch(Dispatchers.Main) {
              viewModel.uiState.collect{ state ->
                  when (state){
                      is LoginActivityViewModel.State.Idle -> idle()
                      is LoginActivityViewModel.State.Error -> showError(state.message)
                      is LoginActivityViewModel.State.Loading -> showLoading()
                      is LoginActivityViewModel.State.SucessLogin -> showSuccessLogin(state.token)
                  }
              }
            }
        }


    private fun showSuccessLogin(token: String) {
        prefRepository.saveTokenPreferences(token)

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

    private fun showError(message: String) {
        Toast.makeText(binding.root.context, "Error Login", Toast.LENGTH_SHORT).show()
    }

    private fun idle() {
        Log.w("HEROES ACTIVITY", "ESPERANDO INSTRUCCIONES")
    }


}