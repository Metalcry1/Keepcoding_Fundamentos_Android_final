package com.example.practica_fundamentos_android_v2.Data.Models

data class Hero(
    val name: String,
    val photo: String,
    val maxLife: Int = 100 ,
    var currentLife: Int = 100,
)
