package com.example.practica_fundamentos_android_v2.Data.Local

import android.content.Context
import com.example.practica_fundamentos_android_v2.Models.Hero

class PrefRepository(val context: Context) {

    val SHARED_NAME = "Mydtb"
    val SHARE_TOKEN = "Token"

    private val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveTokenPreferences(token: String) {
        storage.edit().putString(SHARE_TOKEN, token).apply()
    }

    fun loadTokenPreferences(): String {
       return storage.getString(SHARE_TOKEN, "")!!
    }

    fun loadHeroAlivePreferences(hero: Hero): Boolean{
        return storage.getBoolean(hero.name, true)
    }

    fun saveHeroAlivePreferences(hero: Hero, alive: Boolean){
        storage.edit().putBoolean(hero.name, alive).apply()
    }

}