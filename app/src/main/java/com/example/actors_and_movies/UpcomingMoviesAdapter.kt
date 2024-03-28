package com.example.actors_and_movies  //needed to updated this to the right package name

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actors_and_movies.R
import com.example.actors_and_movies.UpcomingMovie
//import com.example.actors_and_movies.DetailAc

const val MOVIE_EXTRA = "MOVIE_EXTRA"

class UpcomingMoviesAdapter(private val context: Context, private val movies: List<UpcomingMovie>) :
    RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val mediaImageView: ImageView = itemView.findViewById(R.id.mediaImage)
        private val titleTextView: TextView = itemView.findViewById(R.id.mediaTitle)
        private val overviewTextView: TextView = itemView.findViewById(R.id.mediaAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: UpcomingMovie) {
            titleTextView.text = movie.title
            overviewTextView.text = movie.overview

            val imagePath = "https://image.tmdb.org/t/p/w500/" + (movie.backdropPath ?: movie.posterPath)
            Glide.with(context)
                .load(imagePath)
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            val movie = movies[absoluteAdapterPosition]

            //Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("MOVIE_EXTRA", movies[absoluteAdapterPosition]) //note that in order for this function to work, upcomingMovie needs to be set to java.io.serializable
            context.startActivity(intent)
/*
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent) */
        }
    }
}
