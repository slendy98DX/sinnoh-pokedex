package com.example.pokedex.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.pokedex.R
import com.example.pokedex.network.Pokemon

@BindingAdapter("pokemonImage")
fun loadPokemonImage(imageView: ImageView, imageUrl: String?) {
        imageView.load(imageUrl) {
            error(R.drawable.unown_1)
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
    textView.text = name ?: "???"
}

@BindingAdapter("pokemonDetails")
fun setPokemonDetails(textView: TextView, pokemon: Pokemon?) {
    val details = buildString {
        append("Type: ${pokemon?.types?.joinToString() ?: "Unknown"} | ")

        pokemon?.height?.let { append("Height: $it m | ") }
        pokemon?.weight?.let { append("Weight: $it kg") }

        // Trim any trailing " | " if both height and weight are present
        if (endsWith(" | ")) {
            setLength(length - 3)
        }
    }
    textView.text = details
}

@BindingAdapter("pokemonDescription")
fun setPokemonDescription(textView: TextView, description: String?) {
    textView.text = description ?: "???"
}


