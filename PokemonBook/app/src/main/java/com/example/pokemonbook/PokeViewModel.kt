package com.example.pokemonbook

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.pokemonbook.data.Pokemon
import com.example.pokemonbook.data.PokemonDataBase
import com.example.pokemonbook.data.PokemonRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException

class PokeViewModel(private val repository: PokemonRepository) : ViewModel() {
    var rightOrder: MutableList<Pokemon> = mutableListOf()
    val allPokemon: LiveData<MutableList<Pokemon>> = repository.allPokemon
    val scope = CoroutineScope(Dispatchers.IO)
    var poketmp: MutableList<Pokemon> = mutableListOf()
    val url =
        "https://gist.githubusercontent.com/mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38"
    val request = Request.Builder().url(url).build()
    val client = OkHttpClient()

    init {
        if (allPokemon.value.isNullOrEmpty()) {
            viewModelScope.launch { waitApi() }
        }

    }

    @JvmName("getRightOrder1")
    fun getRightOrder(): MutableList<Pokemon> {
        rightOrder = repository.allPokemon.value!!
        return rightOrder
    }

    private suspend fun waitApi() {
        scope.launch {
            val result = callApi(client, request)
            repository.addALL(*result.toTypedArray())
        }.join()
        rightOrder = repository.allPokemon.value!!
        MainActivity.pokemonListtmp=repository.allPokemon.value!!
    }

    fun addPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            repository.add(pokemon)
        }
    }

    fun byRank(rank: Int) {
        poketmp = rightOrder
        when (rank) {
            0 -> {
                poketmp = rightOrder
                for (i in 0 until poketmp.size) {
                    for (j in 0 until poketmp.size - i - 1) {
                        if (poketmp[j].id < poketmp[j + 1].id) {
                            val tmp = poketmp[j + 1]
                            poketmp[j + 1] = poketmp[j]
                            poketmp[j] = tmp
                        }
                    }
                }


            }
            1 -> {
                poketmp = rightOrder
                for (i in 0 until poketmp.size) {
                    for (j in 0 until poketmp.size - i - 1) {
                        if (poketmp[j].attack < poketmp[j + 1].attack) {
                            val tmp = poketmp[j + 1]
                            poketmp[j + 1] = poketmp[j]
                            poketmp[j] = tmp
                        } else if (poketmp[j].attack == poketmp[j + 1].attack) {
                            if (poketmp[j].id > poketmp[j + 1].id) {
                                val tmp = poketmp[j + 1]
                                poketmp[j + 1] = poketmp[j]
                                poketmp[j] = tmp
                            }
                        }
                    }
                }
            }
            2 -> {
                for (i in 0 until poketmp.size) {
                    for (j in 0 until poketmp.size - i - 1) {
                        if (poketmp[j].defense < poketmp[j + 1].defense) {
                            val tmp = poketmp[j + 1]
                            poketmp[j + 1] = poketmp[j]
                            poketmp[j] = tmp
                        } else if (poketmp[j].attack == poketmp[j + 1].attack) {
                            if (poketmp[j].id > poketmp[j + 1].id) {
                                val tmp = poketmp[j + 1]
                                poketmp[j + 1] = poketmp[j]
                                poketmp[j] = tmp
                            }
                        }
                    }
                }
            }
            else -> {
                for (i in 0 until poketmp.size) {
                    for (j in 0 until poketmp.size - i - 1) {
                        if (poketmp[j].speed < poketmp[j + 1].speed) {
                            val tmp = poketmp[j + 1]
                            poketmp[j + 1] = poketmp[j]
                            poketmp[j] = tmp
                        } else if (poketmp[j].attack == poketmp[j + 1].attack) {
                            if (poketmp[j].id > poketmp[j + 1].id) {
                                val tmp = poketmp[j + 1]
                                poketmp[j + 1] = poketmp[j]
                                poketmp[j] = tmp
                            }
                        }
                    }
                }
            }
        }
        Log.i("rank",poketmp[0].name,null)
        MainActivity.pokemonListtmp=poketmp

    }


    fun allPokeCate(): MutableList<String> {
        val cate = mutableListOf<String>()
        for (i in 0 until allPokemon.value?.size!!) {
            val type = allPokemon.value!![i].typeofpokemon
            for (i in 0 until type.size) {
                if (!cate.contains(type[i])) {
                    cate.add(type[i])
                }
            }
        }
        return cate
    }

    fun allPokemon(): MutableList<Pokemon> {
        viewModelScope.launch {
            repository.allPokemon
        }
        val tmp = mutableListOf<Pokemon>()
        for (i in 0 until repository.allPokemon.value?.size!!) {
            repository.allPokemon.value?.get(i)?.let { tmp.add(it) }
        }
        return tmp
    }


    private suspend fun callApi(client: OkHttpClient, request: Request): MutableList<Pokemon> {
        return suspendCancellableCoroutine { continuation ->
            println("RequestUrl: ${request.url}")
            println("")
            val call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed to execute Request")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val body = response.body!!.string()
                        val result = GsonBuilder().create().fromJson<MutableList<Pokemon>>(
                            body,
                            object : TypeToken<MutableList<Pokemon>>() {}.type
                        )
                        try {
                            continuation.resumeWith(Result.success(result))
                        } catch (e: Exception) {
                            println("error")
                        }
                        continuation.invokeOnCancellation {
                            println("$request+cancle")
                            call.cancel()
                        }
                    }
                }

            })
        }
    }


}

class PokeViewModelFactory(private val repository: PokemonRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PokeViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return PokeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}