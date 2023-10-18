package com.example.and101_week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.fetchButton)

        button.setOnClickListener{
            var imageURL = fetchNewData(this)
        }

    }

    private fun fetchNewData(MainActivity: MainActivity){

        val client = AsyncHttpClient()

        var randomNumber = Random.nextInt(0, 1000)

        client["https://pokeapi.co/api/v2/pokemon/$randomNumber", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // Access a JSON object response with `json.jsonObject`
                Log.d("DEBUG OBJECT", json.jsonObject.toString())
                Log.d("DEBUG OBJECT", json.jsonObject.getString("name"))

                var Name = json.jsonObject.getString("name")

                var ID = json.jsonObject.getString("id")

                var NameField = findViewById<TextView>(R.id.NameText)

                NameField.setText("Name: $Name")

                var IdField = findViewById<TextView>(R.id.IdText)

                var imageView = findViewById<ImageView>(R.id.imageHolder)

                IdField.setText("ID: $ID")

                var sprites = json.jsonObject.getJSONObject("sprites")

                var imageURL = sprites.getString("front_default")

                Glide.with(MainActivity)
                    .load(imageURL)
                    .fitCenter()
                    .into(imageView)


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