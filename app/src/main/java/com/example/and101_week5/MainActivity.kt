package com.example.and101_week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var pokeUrlList: MutableList<String>

    private lateinit var pokeList: MutableList<MutableList<String>>

    private lateinit var recyclerViewPoke : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewPoke = findViewById<RecyclerView>(R.id.poke_recycler_view)

        var button = findViewById<Button>(R.id.fetchButton)

        pokeUrlList = mutableListOf()

        pokeList = mutableListOf()

        fetchNewData()

        button.setOnClickListener{

            Log.d("DEBUG OBJECT", pokeList.toString())

            val pokeAdapter = PokeAdapter(pokeList)

            recyclerViewPoke.adapter = pokeAdapter

            recyclerViewPoke.layoutManager = LinearLayoutManager(this@MainActivity)

            recyclerViewPoke.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

            button.visibility = View.GONE
        }

    }

    private fun fetchNewData(){

        val client = AsyncHttpClient()

        client["https://pokeapi.co/api/v2/pokemon?limit=20&offset=0", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON object response with `json.jsonObject`
                Log.d("DEBUG OBJECT", json.jsonObject.toString())
                Log.d("DEBUG OBJECT", json.jsonObject.getString("results"))

                var manyPokemon = json.jsonObject.getJSONArray("results")

                for ( i in 0 until manyPokemon.length()){
                    Log.d("DEBUG OBJECT",manyPokemon.getJSONObject(i).getString("url"))
                    pokeUrlList.add(manyPokemon.getJSONObject(i).getString("url"))
                }

                pokemonDataFetcher()

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.d("PokeAPI FAIL", response)
            }
        }]


    }

    private fun pokemonDataFetcher(){

        val client = AsyncHttpClient()

        for (i in 0 until pokeUrlList.size) {

            client[pokeUrlList[i], object :
                JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    // Access a JSON object response with `json.jsonObject`
                    Log.d("DEBUG OBJECT", json.jsonObject.toString())
                    Log.d("DEBUG OBJECT", json.jsonObject.getString("id"))

                    var test: MutableList<String> = mutableListOf()

                    test.add(json.jsonObject.getString("name"))

                    test.add( json.jsonObject.getString("id"))

                    var sprites = json.jsonObject.getJSONObject("sprites")

                    test.add(sprites.getString("front_default"))

                    pokeList.add(test)

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String,
                    throwable: Throwable?
                ) {
                    Log.d("PokeAPI FAIL", response)
                }
            }]
        }


    }
}