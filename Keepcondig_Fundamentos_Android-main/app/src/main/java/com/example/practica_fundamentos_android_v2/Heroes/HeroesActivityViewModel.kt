package com.example.practica_fundamentos_android_v2.Heroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica_fundamentos_android_v2.Models.Hero
import com.example.practica_fundamentos_android_v2.Models.HeroDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HeroesActivityViewModel: ViewModel() {

    val BASE_URL = "https://dragonball.keepcoding.education/api/"

    private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState : StateFlow<State> = _uiState
    private var token = "eyJ0eXAiOiJKV1QiLCJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiJCRjFBMDE5Qy1CRkFGLTQzOEYtOTBGRC03OEQ2N0JEOTNEQjciLCJlbWFpbCI6Im1ldGFsY3J5MUBnbWFpbC5jb20ifQ.Hp0hmOav5nvYng9MyAxDgU0mGsZFlxuhptWqnOAolUs"
    private val maxLife = 100
    private var  currentLife = 100

        sealed class State {
        class Idle : State()
        class Error(val message: String) : State()
        class Loading : State()
        class SucessGetHeroes(val heroList: List<Hero>) : State()
    }

    fun lauchGetHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading()
            val client = OkHttpClient()
            val url = "${BASE_URL}heros/all"
            val formBody = FormBody.Builder() // Esto hace que la request sea de tipo POST
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .post(formBody)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            _uiState.value =  if (response.isSuccessful)
                response.body?.let {
                    val heroesDtoArray : Array<HeroDto> = Gson().fromJson(it.string(), Array<HeroDto>::class.java)
                    //Aqui guardo solo nombres y puedo añadir vida
                    val heroArray = heroesDtoArray.map {
                        Hero(
                            it.name,
                            it.photo
                        )
                    }

                    State.SucessGetHeroes(heroArray)
                } ?: State.Error("Empty Token")
            else
                State.Error(response.message)
        }
    }
}