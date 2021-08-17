package com.example.pokemonbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "Pokemon_table")
data class Pokemon(


    @PrimaryKey @ColumnInfo(name="name")
    val name: String,
    val id: String,
    val imageurl: String,
    val xdescription: String,
    val ydescription: String,
    val height: String,
    val category: String,
    val weight: String,
    val typeofpokemon: MutableList<String>,
    val weaknesses: MutableList<String>,
    val evolutions: MutableList<String>,
    val abilities: MutableList<String>,
    val hp: String,
    val attack: String,
    val defense: String,
    val special_attack: String,
    val special_defense: String,
    val speed: String,
    val total: String,
    val male_percentage: String?,
    val female_percentage: String?,
    val genderless: String,
    val cycles: String,
    val egg_groups: String,
    val evolvedfrom: String,
    val reason: String,
    val base_exp: String
)


class PokeConverter {
    @TypeConverter
    fun fromString(value: String?): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String>?): String {
        return Gson().toJson(list)
    }

}
