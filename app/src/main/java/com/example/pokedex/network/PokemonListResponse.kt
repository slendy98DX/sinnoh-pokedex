package com.example.pokedex.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListResponse (
    @Json(name = "pokemons") val pokemons: List<Pokemon>
)