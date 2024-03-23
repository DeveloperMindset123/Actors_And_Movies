package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.actors_and_movies.R
import com.example.actors_and_movies.UpcomingMovie

private const val TAG = "DetailActivity"

//this file will handle displaying detail related to the articles

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        bylineTextView = findViewById(R.id.mediaByline)
        abstractTextView = findViewById(R.id.mediaAbstract)

        // Get the extra from the Intent
        val upcoming_movie = intent.getSerializableExtra(ARTICLE_EXTRA) as UpcomingMovie  //ARTICLE_EXTRA has been defined in the ArticleAdapterFile

        // Set the title, byline, and abstract information from the article (we have already defined the text view) --> the classes in the data files ahs not yet been created
        titleTextView.text = upcoming_movie.headline?.main  //refer to the data model file Article.kt, we are parsing the json to retrieve the individual details of the correspoding article
        bylineTextView.text = upcoming_movie.byline?.original  //the val main in headline was changed to val original in Article.kt file
        abstractTextView.text = upcoming_movie.movie_overview  //replaced from "abstract" to "movie_overview"

        // Load the media image using glide
        // Load the media image
        //use glide for the movie image view
        var image_base_path : String = "https://image.tmdb.org/t/p/w500/"

        Glide.with(this)
            .load(image_base_path + upcoming_movie.movieBackdropPath)
            .into(mediaImageView)  //if movieBackdropPath doesn't work, try poster_path instead
    }
}