package com.example.practica_fundamentos_android_v2.Data.Database

import android.app.Application

class PreferenceAplication: Application() {

    companion object{
        lateinit var prefRepository: PrefRepository
    }

    override fun onCreate() {
        super.onCreate()
        prefRepository = PrefRepository(applicationContext)
    }
}