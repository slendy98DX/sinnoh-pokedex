package com.example.pokedex.network.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.network.Pokemon
import com.example.pokedex.network.PokemonApi
import kotlinx.coroutines.launch

enum class PokemonApiStatus {LOADING, ERROR, DONE}

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _selectedPokemon = MutableLiveData<Pokemon>()
    val selectedPokemon: LiveData<Pokemon> = _selectedPokemon

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status


    fun fetchPokemons(){
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING
            try {
                val pokemonList = PokemonApi.retrofitService.getPokemonList().pokemons
                _pokemonList.value = pokemonList.toMutableList()
                _status.value = PokemonApiStatus.DONE
            } catch (e: Exception) {
                _pokemonList.value = mutableListOf()
                _status.value = PokemonApiStatus.ERROR
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