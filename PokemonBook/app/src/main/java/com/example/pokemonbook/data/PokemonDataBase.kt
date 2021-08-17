package com.example.pokemonbook.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(PokeConverter::class)

abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokeDao(): PokemonDao


    private class PokeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var wordDao = database.pokeDao()
//
//                    // Add sample words.
//                    var word = Word("Hello")
//                    wordDao.insert(word)
//                    word = Word("World!")
//                    wordDao.insert(word)
//
//                    // TODO: Add your own words!
//                    word = Word("TODO!")
//                    wordDao.insert(word)
//                }
//            }
        }
    }
    companion object {

        @Volatile
        private var INSTANCE: PokemonDataBase? = null

        fun getDataBase(context: Context):PokemonDataBase{

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDataBase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}