package com.example.pokedex.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.pokedex.network.Pokemon

@BindingAdapter("pokemonImage")
fun loadPokemonImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        imageView.load(it)
    }
}

@BindingAdapter("pokemonNumber")
fun setPokemonNumber(textView: TextView, number: Int?) {
    number?.let {
        textView.text = "Pokemon #$number"
    }
}

@BindingAdapter("pokemonName")
fun setPokemonName(textView: TextView, name: String?) {
    name?.let {
        textView.text = name
    }
}

@BindingAdapter("pokemonDetails")
fun setPokemonDetails(textView: TextView, pokemon: Pokemon?) {
    pokemon?.let {
        val details = "Type: ${it.types.joinToString()} | Height: ${it.height}m | Weight: ${it.weight}kg"
        textView.text = details
    }
}

@BindingAdapter("pokemonDescription")
fun setPokemonDescription(textView: TextView, description: String?) {
    textView.text = description
}


