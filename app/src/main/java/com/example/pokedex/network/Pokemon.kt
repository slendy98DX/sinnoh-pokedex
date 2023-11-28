package com.example.pokedex.network

import com.squareup.moshi.Json

data class Pokemon (

    @Json(name = "num") val id: Int,

    val name: String,

    val types: List<String>,

    val height: Int,

    val weight: Int,

    val description: String
)