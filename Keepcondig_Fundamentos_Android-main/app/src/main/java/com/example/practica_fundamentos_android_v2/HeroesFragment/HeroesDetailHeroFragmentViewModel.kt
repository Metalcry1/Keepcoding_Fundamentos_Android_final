package com.example.practica_fundamentos_android_v2.HeroesFragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HeroesDetailHeroFragmentViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState : StateFlow<State> = _uiState
    private var token = ""

    sealed class State {
        class Idle : State()
        class Error(val message: String) : State()
        class Loading : State()
        //class SucessLogin(var token: String) : State()
    }

}