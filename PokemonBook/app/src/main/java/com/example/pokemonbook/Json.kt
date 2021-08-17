package com.example.pokemonbook

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Json {




    fun convert(jsonText: String): JSONArray {
        val jSONArray: MutableList<JSONObject> = mutableListOf<JSONObject>()

        try {
            val jsonObject: JSONObject = JSONObject(jsonText)
            jSONArray.add(jsonObject)

        } catch (e: JSONException) {
            e.printStackTrace();
        }
        return JSONArray(jsonText)
    }


}