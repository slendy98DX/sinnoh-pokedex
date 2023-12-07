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
        if (!pokemon.isSelected) {
            // Scale up the view when selected
            view.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(10)
                .withEndAction {
                    // Perform your click action here
                    clickListener(pokemon)

                    // Restore the original scale after the click action is done
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(10)
                        .start()
                }
        } else {
            // Optionally, you can add a different animation for deselection
            // For example, fade out the view when deselected
            view.animate()
                .alpha(0.5f)
                .setDuration(10)
                .withEndAction {
                    // Perform your click action here (for deselection)
                    clickListener(pokemon)

                    // Restore the original alpha after the click action is done
                    view.animate()
                        .alpha(1.0f)
                        .setDuration(10)
                        .start()
                }
        }
    }
}