package com.example.pokemonbook.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.MainActivity
import com.example.pokemonbook.R
import com.example.pokemonbook.ViewHolder.CateHolder
import com.example.pokemonbook.data.Pokemon

class cateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokeCate: List<String> = emptyList()
    private var pokemon: List<Pokemon> = emptyList()
    private var pokemonCate = mutableListOf<Pokemon>()
    private var open = false
    val childAdapter = childAdapter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cate, parent, false)
        return CateHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cate = pokeCate[position]
        val color = when (cate.toLowerCase()) {
            "grass" -> "#2CDAB1"
            "fire" -> "#F7706B"
            "water" -> "#58ABF6"
            "bug" -> "#2CDA90"
            "normal" -> "#7986CB"
            "poison" -> "#9F5BBA"
            "electric" -> "#FFE252"
            "ground" -> "#CA8179"
            "fairy" -> "#FF86AF"
            "fighting" -> "#F78C6B"
            "psychic" -> "#FFCE4B"
            "rock" -> "#955A54"
            "ghost" -> "#7C5BBA"
            "ice" -> "#58F1F6"
            "dragon" -> "#7A9A64"
            "dark" -> "#303943"
            "steel" -> "#4A425A"
            "flying" -> "#C379CB"
            else -> "#7986CB"
        }

        holder.itemView.apply {
            val button = findViewById<Button>(R.id.cateButton)
            button.text = cate
            button.setBackgroundColor(Color.parseColor(color))

            button.setOnClickListener {
                var pokemonCatetmp = mutableListOf<Pokemon>()
                val recyclerView = findViewById<RecyclerView>(R.id.childRV)

                if (open) {
                    recyclerView.adapter = null
                    open = false
                } else {
                    open = true
                    recyclerView.adapter = childAdapter
                    for (i in 0 until pokemon.size) {
                        val type = pokemon[i].typeofpokemon
                        for (f in 0 until type.size) {
                            if (cate.contains(type[f])) {
                                pokemonCatetmp.add(pokemon[i])
                            }
                        }
                    }
                }
                childAdapter.refreshItems(pokemonCatetmp, color)
            }

        }


    }

    override fun getItemCount(): Int {
        return pokeCate.size
    }

    fun refreshItems(pokeCate: List<String>, pokemon: List<Pokemon>) {
        this.pokeCate = pokeCate
        this.pokemon = pokemon
        notifyDataSetChanged()
    }
}