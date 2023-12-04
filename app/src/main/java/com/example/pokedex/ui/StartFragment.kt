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

    private val sharedViewModel: PokemonViewModel by activityViewModels()
    private var binding: FragmentStartBinding? = null
    private var adapter: PokemonListAdapter? = null

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
            viewModel = sharedViewModel
        }

        adapter = PokemonListAdapter(PokemonListener { clickedPokemon ->
            sharedViewModel.selectPokemon(clickedPokemon)
        })

        setupRecyclerView()
        observeViewModel()

        sharedViewModel.fetchPokemons()

        // Observe the selected Pokémon
        sharedViewModel.selectedPokemon.observe(viewLifecycleOwner) { selectedPokemon ->
            selectedPokemon?.let { updatePokemonDetails(it) }
        }
    }

    private fun observeViewModel() {
        sharedViewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            // Update UI with the Pokémon data
            if (pokemonList.isNotEmpty()) {
                Log.d("PokemonFragment", "Pokémon list size: ${pokemonList.size}")
                adapter?.submitList(pokemonList)

                // If no Pokémon is selected, select the first one
                if (sharedViewModel.selectedPokemon.value == null) {
                    sharedViewModel.selectPokemon(pokemonList[0])
                }
            }
        }
    }

    private fun updatePokemonDetails(pokemon: Pokemon) {
        // Using Coil to load the Pokemon image
        binding?.pokemonImageView?.load(pokemon.img)

        // Update other details
        binding?.pokemonNumberTextView?.text = "Pokemon #${pokemon.num}"
        binding?.pokemonNameTextView?.text = pokemon.name
        binding?.pokemonTypeHeightWeightTextView?.text =
            "Type: ${pokemon.types.joinToString()} | Height: ${pokemon.height}m | Weight: ${pokemon.weight}kg"
        binding?.pokemonDescriptionTextView?.text = pokemon.description
    }

    private fun setupRecyclerView() {
        binding?.pokemonListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.pokemonListRecyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}