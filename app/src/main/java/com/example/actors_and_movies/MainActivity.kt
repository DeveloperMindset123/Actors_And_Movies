package com.example.actors_and_movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.UpcomingMoviesAdapter
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.actors_and_movies.databinding.ActivityMainBinding
import kotlinx.serialization.decodeFromString
import okhttp3.Headers
import org.json.JSONException
import kotlinx.serialization.json.Json


/**
 * shell request (copied from the API reference):
 * curl --request GET \
 *      --url 'https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1' \
 *      --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MTFkOGNlMmM3ZGM3N2IwNDJlZGJhNzVhYzM2MjUzYyIsInN1YiI6IjY1ZmRjMzAzMGMxMjU1MDE2NTBiYjIyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FJ-rcPjIZ7ekxcrTxz8w1PJIhkjIXDgy1ZBew8wIczc' \
 *      --header 'accept: application/json'
 *      811d8ce2c7dc77b042edba75ac36253c --> this is my own API key
 *
 *      API Read Access token: eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MTFkOGNlMmM3ZGM3N2IwNDJlZGJhNzVhYzM2MjUzYyIsInN1YiI6IjY1ZmRjMzAzMGMxMjU1MDE2NTBiYjIyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FJ-rcPjIZ7ekxcrTxz8w1PJIhkjIXDgy1ZBew8wIczc

Sample request from kotlin:
val client = OkHttpClient()

val request = Request.Builder()
.url("https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1")
.get()
.addHeader("accept", "application/json")
.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MTFkOGNlMmM3ZGM3N2IwNDJlZGJhNzVhYzM2MjUzYyIsInN1YiI6IjY1ZmRjMzAzMGMxMjU1MDE2NTBiYjIyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FJ-rcPjIZ7ekxcrTxz8w1PJIhkjIXDgy1ZBew8wIczc")
.build()

val response = client.newCall(request).execute()


 The following is the sample request to get a list of people based on populatiry: (according to the API ref)

val client = OkHttpClient()

val request = Request.Builder()
.url("https://api.themoviedb.org/3/person/popular?language=en-US&page=1")
.get()
.addHeader("accept", "application/json")
.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MTFkOGNlMmM3ZGM3N2IwNDJlZGJhNzVhYzM2MjUzYyIsInN1YiI6IjY1ZmRjMzAzMGMxMjU1MDE2NTBiYjIyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FJ-rcPjIZ7ekxcrTxz8w1PJIhkjIXDgy1ZBew8wIczc")
.build()

val response = client.newCall(request).execute()


 *
 * */

//API endpoint: https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1&api_key=811d8ce2c7dc77b042edba75ac36253c  --> tested to see if it works as intended
private const val TAG="MainActivity/"
private const val API_KEY="a07e22bc18f5cb106bfe4cc1f83ad8ed"  //provided by codepath
private const val UPCOMING_MOVIE_URL="https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1&api_key=${API_KEY}"  //this API key will send a response back containing the list of upcoming movies

private const val POPULAR_ACTORS_URL = "https://api.moviedb.org/3/person/popular?language=en-US&page=1&api_key=${API_KEY}"  //this API key will send a response back containing the list of popular actors


class MainActivity : AppCompatActivity() {
    private val upcoming_movies = mutableListOf<UpcomingMovie>()
    private lateinit var upcomingMovies_Recycler_View : RecyclerView
    private lateinit var binding : ActivityMainBinding  //needs gradle configuration to work

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //calling the action bar
        val actionbar = getSupportActionBar()

        //showing back button
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        upcomingMovies_Recycler_View = findViewById(R.id.articles)
        val upcomingMovies_adapter = UpcomingMoviesAdapter(this, upcoming_movies)
        upcomingMovies_Recycler_View.adapter = upcomingMovies_adapter   //set the adapter to the newly instantiated adapter

        //layout manager logic
        upcomingMovies_Recycler_View.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            upcomingMovies_Recycler_View.addItemDecoration(dividerItemDecoration)
        }

        //progress bar animation logic
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE   //make the progress bar visible while data is fetched

        var client = AsyncHttpClient()
        client.get(UPCOMING_MOVIE_URL, object : JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                //hide the progress bar if the data has been successfully fetched
                progressBar.visibility = View.INVISIBLE
                Log.i(TAG, "Successfully fetched articles: $json")

                try {
                    // Do something with the returned json (contains article information)
                    val parsedJson = Json {ignoreUnknownKeys=true} .decodeFromString<UpcomingMovies_API_Response>(
                        json?.jsonObject.toString()
                    )


                    parsedJson.response?.docs?.let { list ->
                        upcoming_movies.addAll(list)

                        //Reload the screen
                        upcomingMovies_adapter.notifyDataSetChanged()
                    }
            } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }

            }

        })}}


