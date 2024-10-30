package com.example.pokemonapp.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.models.PokemonListModel
import com.example.pokemonapp.repository.PokemonListRepository
import com.example.pokemonapp.repository.PokemonListRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonListRepositoryInterface = PokemonListRepository()
): ViewModel() {
    private val _pokemonList = MutableStateFlow<PokemonListModel?>(null)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(true)

    val pokemonList: StateFlow<PokemonListModel?> get() = _pokemonList.asStateFlow()
    val errorMessage: StateFlow<String?> get() = _errorMessage.asStateFlow()
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun getPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getPokemonList(0, 150)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("Success", "$body?.size")
                    _isLoading.value = false
                    _pokemonList.value = body
                }
            } else {
                val error = response.errorBody()
                if (error != null) {
                    Log.d("Pokemon List Error", error.string())
                    _isLoading.value = false
                    _errorMessage.value = error.string()
                }
            }
        }
    }
}
