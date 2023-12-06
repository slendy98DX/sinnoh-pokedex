package com.example.pokedex.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.pokedex.R
import com.example.pokedex.network.Pokemon
import com.example.pokedex.network.model.PokemonApiStatus

@BindingAdapter("pokemonImage")
fun loadPokemonImage(imageView: ImageView, imageUrl: String?) {
        imageView.load(imageUrl) {
            error(R.drawable.unown_1)
            placeholder(R.drawable.pok__ball_icon)
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

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: PokemonApiStatus?) {
    when (status) {
        PokemonApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        PokemonApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }

        PokemonApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        else -> {

        }
    }
}


