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

    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon> = _selectedPokemon


    fun fetchPokemons(){
        viewModelScope.launch {
            try {
                val pokemonList = PokemonApi.retrofitService.getPokemonList().pokemons
                _pokemonList.value = pokemonList.toMutableList()
            } catch (e: Exception) {
                _pokemonList.value = mutableListOf()
            }
        }
    }

    fun selectPokemon(pokemon: Pokemon) {
        _selectedPokemon.value = pokemon
    }

    init{
        fetchPokemons()
    }
}