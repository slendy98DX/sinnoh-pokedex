package com.example.pokedex.network.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.Pokemon
import com.example.pokedex.network.PokemonApi
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private fun fetchPokemons(){
        viewModelScope.launch {
            try {
                _pokemonList.value = PokemonApi.retrofitService.getPokemonList().pokemons.toMutableList()
            } catch (e: Exception) {
                _pokemonList.value = mutableListOf()
            }
        }
    }

    init{
        fetchPokemons()
    }
}