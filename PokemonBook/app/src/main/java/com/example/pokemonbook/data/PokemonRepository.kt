package com.example.pokemonbook.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class PokemonRepository (private val pokemonDao: PokemonDao){


    val allPokemon : LiveData<MutableList<Pokemon>> =pokemonDao.readAllData()

   suspend fun add(pokemon: Pokemon){
        pokemonDao.addPokemon(pokemon)
    }

    suspend fun addALL(vararg pokemon: Pokemon){
        pokemonDao.insertAll(*pokemon)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pokemon: Pokemon) {
        pokemonDao.addPokemon(pokemon)
    }

}