package com.example.pokedex.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val URL : String = "https://github.com/brunomoreirazup/kanto-pokedex-json/blob/master/pokedex.json"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()


interface PokedexApiService {

    @GET(URL)
    suspend fun getPokemon() : List<Pokemon>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: PokedexApiService by lazy {
        retrofit.create(PokedexApiService::class.java)
    }
}