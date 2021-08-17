package com.example.pokemonbook
import android.util.Log
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonbook.adapter.typeAdapter
import com.example.pokemonbook.data.Pokemon
import com.example.pokemonbook.data.PokemonApplication
import com.squareup.picasso.Picasso

class MainActivity2 : AppCompatActivity() {

    private val pokeViewModel: PokeViewModel by viewModels {
        PokeViewModelFactory((application as PokemonApplication).repository)
    }
    var rightOrderPoke: MutableList<Pokemon> = mutableListOf()

    companion object {
        lateinit var pokemonPage: Pokemon
        var poketmp: MutableList<Pokemon> = mutableListOf()
        var position = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val recycler = findViewById<RecyclerView>(R.id.insidepokemon)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val typeAdapter = typeAdapter()
        recycler.adapter = typeAdapter
        poketmp = pokeViewModel.allPokemon()
        for (i in 0 until poketmp.size) {
            if (poketmp[i].name == pokemonPage.name) {
                position = i
            }
        }
        rightOrderPoke = pokeViewModel.getRightOrder()
        val heart = findViewById<ImageView>(R.id.insideHeart)
        for (i in 0 until MainActivity.likePokemonList.size) {
            if (MainActivity.likePokemonList.contains(pokemonPage)) {
                heart.setColorFilter(Color.parseColor("#D20F0F"))
            }
        }

        val back = findViewById<TextView>(R.id.back)
        val name = findViewById<TextView>(R.id.name)
        name.text = pokemonPage.name
        val id = findViewById<TextView>(R.id.id)
        id.text = pokemonPage.id.toString()
        val pokemonPic = findViewById<ImageView>(R.id.pokemonpic)
        val url = pokemonPage.imageurl
        Picasso.get().load(url).into(pokemonPic)
        val xdec = findViewById<TextView>(R.id.xdes)
        xdec.text = pokemonPage.xdescription
        val heightNum = findViewById<TextView>(R.id.heightNum)
        heightNum.text = pokemonPage.height
        val weightNum = findViewById<TextView>(R.id.weightNum)
        weightNum.text = pokemonPage.weight
        val hpnum = findViewById<TextView>(R.id.hpNum)
        hpnum.text = pokemonPage.hp
        val atkNum = findViewById<TextView>(R.id.ATKNUM)
        atkNum.text = pokemonPage.attack
        val defNum = findViewById<TextView>(R.id.DEFNUM)
        defNum.text = pokemonPage.defense
        val spdNum = findViewById<TextView>(R.id.SPDNUM)
        spdNum.text = pokemonPage.speed
        val total =
            (pokemonPage.attack.toInt() + pokemonPage.hp.toInt() + pokemonPage.defense.toInt() + pokemonPage.speed.toInt()).toString()
        val ttl = findViewById<TextView>(R.id.totalNum)
        ttl.text = total
        val right = findViewById<ImageView>(R.id.right)
        right.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            if (position == poketmp.size - 1) {
                pokemonPage = rightOrderPoke[0]
            } else {
                Log.i("right order",rightOrderPoke.size.toString(),null)
               pokemonPage = rightOrderPoke[position + 1]
            }
            ContextCompat.startActivity(this, intent, null)
        }
        val left = findViewById<ImageView>(R.id.left)
        left.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            if (position == 0) {
                pokemonPage = rightOrderPoke[rightOrderPoke.size - 1]
            } else {
                pokemonPage = rightOrderPoke[position - 1]
            }
            ContextCompat.startActivity(this, intent, null)
        }

    }
}