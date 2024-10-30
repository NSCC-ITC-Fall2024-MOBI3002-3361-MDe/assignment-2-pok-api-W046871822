package com.example.pokemonapp.repository

import com.example.pokemonapp.models.PokemonDetailsModel
import com.example.pokemonapp.retrofit.APIService
import com.example.pokemonapp.retrofit.getRetrofitClient
import retrofit2.Response

interface PokemonDetailsRepositoryInterface {
    suspend fun getPokemonDetails(name: String): Response<PokemonDetailsModel>
}

class PokemonDetailsRepository(
    private val apiService: APIService = getRetrofitClient()
): PokemonDetailsRepositoryInterface {
    override suspend fun getPokemonDetails(name: String): Response<PokemonDetailsModel> {
        return apiService.getPokemonDetails(name)
    }
}