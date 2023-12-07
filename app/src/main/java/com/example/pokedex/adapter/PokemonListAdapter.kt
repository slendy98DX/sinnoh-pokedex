package com.example.pokedex.adapter

import android.view.LayoutInflater
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
    fun onClick(pokemon: Pokemon) {
        // Check if the Pokemon is already selected
        if (!pokemon.isSelected) {
            pokemon.isSelected = true
            clickListener(pokemon)
        }
    }
}