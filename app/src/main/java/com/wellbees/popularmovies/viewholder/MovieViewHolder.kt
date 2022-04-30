package com.wellbees.popularmovies.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.databinding.MovieItemBinding
import com.wellbees.popularmovies.model.Movie

class MovieViewHolder(
    var context: Context,
    private val binding: MovieItemBinding,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    val baseUrl : String = "https://image.tmdb.org/t/p/original/"

    init {
        itemView.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(movie: Movie) {
        Glide.with(context)
            .load(baseUrl + movie.posterPath)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.imageMovie);
        binding.tvTitle.text = movie.title
        binding.tvCountry.text = movie.releaseDate
    }
}