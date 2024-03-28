package com.example.actors_and_movies


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.actors_and_movies.R
import com.example.actors_and_movies.UpcomingMovie

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        overviewTextView = findViewById(R.id.mediaAbstract)

        val upcomingMovie = intent.getSerializableExtra("MOVIE_EXTRA") as UpcomingMovie

        titleTextView.text = upcomingMovie.title
        overviewTextView.text = upcomingMovie.overview

        val imagePath = if (!upcomingMovie.backdropPath.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w500/${upcomingMovie.backdropPath}"
        } else {
            "https://image.tmdb.org/t/p/w500/${upcomingMovie.posterPath}"
        }

        Glide.with(this)
            .load(imagePath)
            .into(mediaImageView)
    }
}
