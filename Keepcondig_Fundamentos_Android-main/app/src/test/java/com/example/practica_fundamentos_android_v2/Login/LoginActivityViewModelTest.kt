package com.example.practica_fundamentos_android_v2.Login

import androidx.constraintlayout.utils.widget.MockView
import com.example.practica_fundamentos_android_v2.UI.Login.LoginActivityViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test


class LoginActivityViewModelTest {
    @RelaxedMockK
    @MockK

    var token: String = "MY_TOKEN"

    lateinit var loginActivityViewModel: LoginActivityViewModel
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        loginActivityViewModel = LoginActivityViewModel()
    }

    @Test


    fun test_whenLoginWithUserAndPassword_thenGetValidToken(){

    }
}