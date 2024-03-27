package com.example.actors_and_movies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.codepath.articlesearch.R
import com.codepath.articlesearch.UpcomingMoviesAdapter
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.decodeFromString
import okhttp3.Headers
import org.json.JSONException
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val movies = mutableListOf<UpcomingMovie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesRecyclerView = findViewById(R.id.recyclerViewMovies)
        progressBar = findViewById(R.id.progressBar)

        val adapter = UpcomingMoviesAdapter(this, movies)
        moviesRecyclerView.adapter = adapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        moviesRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fetchUpcomingMovies(adapter)
    }

    private fun fetchUpcomingMovies(adapter: UpcomingMoviesAdapter) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.get(UPCOMING_MOVIE_URL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                progressBar.visibility = View.GONE
                try {
                    // Decode the JSON response into the ApiResponse object
                    val moviesResponse = Json.decodeFromString<ApiResponse>(json.jsonObject.toString())
                    Log.i("API Response:", moviesResponse.toString())
                    // Clear existing movies and add all new ones from the response
                    movies.clear()
                    movies.addAll(moviesResponse.results)
                    // Notify the adapter to refresh the RecyclerView
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Hit JSON exception $e")
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable) {
                progressBar.visibility = View.GONE
                Log.e(TAG, "Fetch movies failure: $response")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity/"
        private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
        private const val UPCOMING_MOVIE_URL = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1&api_key=$API_KEY"
    }
}
