package com.example.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ListViewItemBinding
import com.example.pokedex.network.Pokemon

class PokemonListAdapter(private val clickListener: PokemonListener) :
    ListAdapter<Pokemon, PokemonListAdapter.PokemonViewHolder>(DiffCallback) {

    class PokemonViewHolder(
        private var binding: ListViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: PokemonListener, pokemon: Pokemon) {
            binding.pokemon = pokemon
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.num == newItem.num
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(
            ListViewItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(clickListener, pokemon)
    }
}

class PokemonListener(val clickListener: (pokemon: Pokemon) -> Unit) {
    fun onClick(pokemon: Pokemon, view: View) {
        // Check if the Pokemon is not already selected
        if (!pokemon.isSelected) {
            // Scale the view on click
            view.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(50) // Set the duration of the animation in milliseconds
                .withEndAction {
                    // Perform your click action here
                    clickListener(pokemon)

                    // Restore the original scale after the click action is done
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(50)
                        .start()
                }
                .start()
        } else {
            // Optionally, you can add a different animation for deselection
            // For example, fade out the view when deselected
            view.animate()
                .alpha(0.5f)
                .setDuration(50)
                .withEndAction {
                    // Perform your click action here (for deselection)
                    clickListener(pokemon)

                    // Restore the original alpha after the click action is done
                    view.animate()
                        .alpha(1.0f)
                        .setDuration(50)
                        .start()
                }
                .start()
        }
    }
}