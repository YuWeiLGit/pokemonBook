package com.example.pokemonbook.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemon(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemon: Pokemon)

    @Query("SELECT * FROM Pokemon_table ORDER BY id ASC")
    fun readAllData():LiveData< MutableList<Pokemon>>


}