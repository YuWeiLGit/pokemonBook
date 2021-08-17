package com.example.pokemonbook.data

import android.app.Application

class PokemonApplication : Application(){



    val database by lazy { PokemonDataBase.getDataBase(this) }
    val repository by lazy { PokemonRepository(database.pokeDao()) }

}