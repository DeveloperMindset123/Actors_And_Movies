package com.example.actors_and_movies  //updated the path so we can correctly access the package in other files


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    //private lateinit var requestOptions : RequestOptions

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

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        //requestOptions = requestOptions.transforms(new CenterCrop ())
        Glide.with(this)
            .load(imagePath)
            .transition(withCrossFade(factory))
            .transform(RoundedCorners(50))
            .into(mediaImageView)

    }
}
