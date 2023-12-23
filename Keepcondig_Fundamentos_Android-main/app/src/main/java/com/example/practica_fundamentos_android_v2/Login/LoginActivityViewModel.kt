package com.example.practica_fundamentos_android_v2.Login
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request


class LoginActivityViewModel: ViewModel(){

    val BASE_URL = "https://dragonball.keepcoding.education/api/"

    private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState : StateFlow<State> = _uiState
    private var token = ""

    sealed class State {
        class Idle : State()
        class Error(val message: String) : State()
        class Loading : State()
        class SucessLogin(var token: String) : State()
    }

    fun lauchLogin(emailField: String, passWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading()
            val client = OkHttpClient()
            val url = "${BASE_URL}auth/login"
            val credentials = Credentials.basic(emailField, passWord)
            val formBody = FormBody.Builder() // Esto hace que la request sea de tipo POST
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", credentials)
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            _uiState.value =  if (response.isSuccessful)
                response.body?.let {
                    token = it.string()
                    Log.w("Login", token)

                    State.SucessLogin(token)
                } ?: State.Error("Empty Token")
            else
                State.Error(response.message)
        }
    }



}


