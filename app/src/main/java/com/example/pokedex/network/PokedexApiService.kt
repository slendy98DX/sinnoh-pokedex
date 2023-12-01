package com.example.pokedex.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://raw.githubusercontent.com/slendy98DX/pokedex-json/main/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface PokedexApiService {

    @GET("sinnoh-pokedex.json") // Replace with your JSON file path in the repository
    suspend fun getPokemonList(): PokemonListResponse
}

object PokemonApi {
    val retrofitService: PokedexApiService by lazy { retrofit.create(PokedexApiService::class.java) }
}