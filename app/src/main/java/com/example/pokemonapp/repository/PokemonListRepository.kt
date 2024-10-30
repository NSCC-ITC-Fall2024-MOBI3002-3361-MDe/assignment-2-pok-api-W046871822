package com.example.pokemonapp.repository

import android.util.Log
import com.example.pokemonapp.models.PokemonListModel
import com.example.pokemonapp.retrofit.APIService
import com.example.pokemonapp.retrofit.getRetrofitClient
import retrofit2.Response

interface PokemonListRepositoryInterface {
    suspend fun getPokemonList(offset: Int, limit: Int): Response<PokemonListModel>
}

class PokemonListRepository(
    private val apiService: APIService = getRetrofitClient()
): PokemonListRepositoryInterface {
    override suspend fun getPokemonList(offset: Int, limit: Int): Response<PokemonListModel> {
        Log.d("Repository getPokemonList", "$offset, $limit")
        return apiService.getPokemonList(offset = offset, limit = limit)
    }
}