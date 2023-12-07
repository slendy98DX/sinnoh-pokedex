package com.example.pokedex.ui

import android .os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.adapter.PokemonListAdapter
import com.example.pokedex.adapter.PokemonListener
import com.example.pokedex.databinding.FragmentStartBinding
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
            if(sharedViewModel.selectedPokemon.value?.name != clickedPokemon.name) {
                sharedViewModel.selectPokemon(clickedPokemon)
            }
        })

        setupRecyclerView()
        observeViewModel()
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

    private fun setupRecyclerView() {
        binding?.pokemonListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.pokemonListRecyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}