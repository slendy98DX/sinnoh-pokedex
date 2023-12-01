package com.example.pokedex.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pokemon(

    @Json(name = "num") val num: Int,
    @Json(name = "img") val img: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val types: List<String>,
    @Json(name = "height") val height: String,
    @Json(name = "weight") val weight: String,
    @Json(name = "description") val description : String

)