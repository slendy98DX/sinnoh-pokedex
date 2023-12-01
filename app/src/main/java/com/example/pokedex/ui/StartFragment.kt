package com.example.pokedex.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.pokedex.adapter.PokemonListAdapter
import com.example.pokedex.adapter.PokemonListener
import com.example.pokedex.databinding.FragmentStartBinding
import com.example.pokedex.network.Pokemon
import com.example.pokedex.network.model.PokemonViewModel

class StartFragment : Fragment() {

    private val viewModel : PokemonViewModel by activityViewModels()
    private var binding: FragmentStartBinding? = null
    private var adapter : PokemonListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            startFragment = this@StartFragment
            viewModel = viewModel
        }

        adapter = PokemonListAdapter(PokemonListener { clickedPokemon ->
            updatePokemonDetails(clickedPokemon)
        })

        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun updatePokemonDetails(pokemon: Pokemon) {
        // Using Coil to load the Pokemon image
        binding?.pokemonImageView?.load(pokemon.img)

        // Update other details
        binding?.pokemonNumberTextView?.text = "Pokemon #${pokemon.num}"
        binding?.pokemonNameTextView?.text = pokemon.name
        binding?.pokemonTypeHeightWeightTextView?.text = "Type: ${pokemon.types} | Height: ${pokemon.height}m | Weight: ${pokemon.weight}kg"
        binding?.pokemonDescriptionTextView?.text = pokemon.description
    }

    private fun observeViewModel() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            // Update UI with the Pokemon data
            if (pokemonList.isNotEmpty()) {
                Log.d("PokemonFragment", "Pokemon list size: ${pokemonList.size}")
                adapter?.submitList(pokemonList)
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.pokemonListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.pokemonListRecyclerView?.adapter = adapter
    }
}