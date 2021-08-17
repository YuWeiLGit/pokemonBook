package com.example.pokemonbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.adapter.cateAdapter
import com.example.pokemonbook.data.Pokemon
import com.example.pokemonbook.data.PokemonApplication
import com.example.pokemonbook.data.PokemonDataBase
import com.example.pokemonbook.data.PokemonRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import androidx.activity.viewModels
import androidx.lifecycle.observe
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {


    private val pokeViewModel: PokeViewModel by viewModels {
        PokeViewModelFactory((application as PokemonApplication).repository)
    }

    companion object {
        var likePokemonList: MutableList<Pokemon> = mutableListOf()
        var pokemonListtmp: MutableList<Pokemon> = mutableListOf()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val Default = findViewById<Button>(R.id.DEAULT)
        val Atk = findViewById<Button>(R.id.ATK)
        val Def = findViewById<Button>(R.id.DEF)
        val Spd = findViewById<Button>(R.id.SPD)
        val cate = mutableListOf<String>()
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val cateAdpter = cateAdapter()
        recyclerView.adapter = cateAdpter
        pokeViewModel.allPokemon.observe(owner = this) { _ ->
            cateAdpter.refreshItems(pokeViewModel.allPokeCate(), pokemonListtmp)
        }

        Default.setOnClickListener {
            pokeViewModel.byRank(0)
            cateAdpter.refreshItems(pokeViewModel.allPokeCate(), pokemonListtmp)
            Default.setBackgroundResource(R.drawable.cliked)
            Atk.setBackgroundResource(R.drawable.unclicked)
            Def.setBackgroundResource(R.drawable.unclicked)
            Spd.setBackgroundResource(R.drawable.unclicked)

        }
        Atk.setOnClickListener {
            pokeViewModel.byRank(1)
            cateAdpter.refreshItems(pokeViewModel.allPokeCate(), pokemonListtmp)
            Default.setBackgroundResource(R.drawable.unclicked)
            Atk.setBackgroundResource(R.drawable.cliked)
            Def.setBackgroundResource(R.drawable.unclicked)
            Spd.setBackgroundResource(R.drawable.unclicked)
        }
        Def.setOnClickListener {

            pokeViewModel.byRank(2)
            cateAdpter.refreshItems(pokeViewModel.allPokeCate(), pokemonListtmp)
            Default.setBackgroundResource(R.drawable.unclicked)
            Atk.setBackgroundResource(R.drawable.unclicked)
            Def.setBackgroundResource(R.drawable.cliked)
            Spd.setBackgroundResource(R.drawable.unclicked)
        }
        Spd.setOnClickListener {
            pokeViewModel.byRank(3)
            cateAdpter.refreshItems(pokeViewModel.allPokeCate(), pokemonListtmp)
            Default.setBackgroundResource(R.drawable.unclicked)
            Atk.setBackgroundResource(R.drawable.unclicked)
            Def.setBackgroundResource(R.drawable.unclicked)
            Spd.setBackgroundResource(R.drawable.cliked)
        }

    }


}





