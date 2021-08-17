package com.example.pokemonbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.R
import com.example.pokemonbook.ViewHolder.typeHolder

class typeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var pokeType: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.typeofpokemon, parent, false)
        return typeHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type=pokeType[position]
        holder.itemView.apply {
            val button=findViewById<Button>(R.id.typebox)
            button.text=type
        }

    }

    override fun getItemCount(): Int {
        Log.i("PPP",pokeType.size.toString(),null)
        return  pokeType.size
    }

    fun refreshItems(pokeType: List<String>) {
        this.pokeType = pokeType
        notifyDataSetChanged()
    }

}