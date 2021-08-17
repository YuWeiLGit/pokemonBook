package com.example.pokemonbook.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.MainActivity
import com.example.pokemonbook.MainActivity2
import com.example.pokemonbook.R
import com.example.pokemonbook.ViewHolder.childHolder
import com.example.pokemonbook.data.Pokemon
import com.squareup.picasso.Picasso


class childAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        lateinit var color: String
    }

    private var pokemon: List<Pokemon> = emptyList()
    lateinit var color: String
    var like = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pokemonlayout1, parent, false)
        return childHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val pokemon = pokemon[position]
        holder.itemView.apply {
            val backGround = findViewById<TextView>(R.id.pokemonBack)
            val name = findViewById<TextView>(R.id.textName)
            val id = findViewById<TextView>(R.id.Id)
            val atkNum = findViewById<TextView>(R.id.atkNUM)
            val defNum = findViewById<TextView>(R.id.defNUM)
            val spdNum = findViewById<TextView>(R.id.spdNUM)
            val pokemonPic = findViewById<ImageView>(R.id.pokemonPic)
            val heart = findViewById<ImageView>(R.id.heart)
            holder.itemView.setOnClickListener{

            }

            for (i in 0 until MainActivity.likePokemonList.size) {
                if (MainActivity.likePokemonList.contains(pokemon)) {
                    heart.setColorFilter(Color.parseColor("#D20F0F"))
                    like=true
                }
            }
            heart.setOnClickListener {
                like = if (like) {
                    MainActivity.likePokemonList.remove(pokemon)
                    heart.setColorFilter(Color.parseColor("#B39E9E"))
                    false
                } else {
                    MainActivity.likePokemonList.add(pokemon)
                    heart.setColorFilter(Color.parseColor("#D20F0F"))
                    true
                }
            }

            backGround.setOnClickListener {
                val intent = Intent(this.context, MainActivity2::class.java)
                MainActivity2.pokemonPage = pokemon

                startActivity(this.context, intent, null)

            }
            backGround.setBackgroundColor(Color.parseColor(color))
            name.text = pokemon.name
            id.text = pokemon.id.toString()
            atkNum.text = pokemon.attack
            defNum.text = pokemon.defense
            spdNum.text = pokemon.speed
            val url = pokemon.imageurl
            Picasso.get().load(url).into(pokemonPic)

        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    fun refreshItems(pokemon: List<Pokemon>, color: String) {
        this.pokemon = pokemon
        this.color = color
        notifyDataSetChanged()

    }
}